package be.cytomine.ontology

import be.cytomine.AnnotationDomain
import be.cytomine.Exception.WrongArgumentException
import be.cytomine.api.UrlApi
import be.cytomine.image.ImageInstance
import be.cytomine.project.Project
import be.cytomine.security.SecUser
import be.cytomine.security.User
import be.cytomine.utils.JSONUtils
import com.vividsolutions.jts.io.WKTReader
import grails.converters.JSON
import org.apache.log4j.Logger

/**
 * An annotation created by a user
 */
class UserAnnotation extends AnnotationDomain implements Serializable {

    User user
    Integer countReviewedAnnotations = 0

    static constraints = {
    }

    static mapping = {
          id generator: "assigned"
          columns {
              location type: org.hibernatespatial.GeometryUserType
          }
         wktLocation(type: 'text')
        sort "id"
      }


    def beforeInsert() {
        super.beforeInsert()
    }

    def beforeUpdate() {
        super.beforeUpdate()
    }

    /**
     * Check if annotation is reviewed
     * @return True if annotation is linked with at least one review annotation
     */
    boolean hasReviewedAnnotation() {
        return countReviewedAnnotations>0
    }

    /**
     * Get all terms map with the annotation
     * @return Terms list
     */
    def terms() {
        if(this.version!=null) {
            AnnotationTerm.findAllByUserAnnotation(this).collect {it.term}
        } else {
            return []
        }
    }

    /**
     * Get all annotation terms id
     * @return Terms id list
     */
    def termsId() {
        if (user.algo()) {
            return AlgoAnnotationTerm.findAllByAnnotationIdent(this.id).collect{it.term?.id}.unique()
        } else {
            return terms().collect{it.id}.unique()
        }

    }

    /**
     * Get all terms for automatic review
     * If review is done "for all" (without manual user control), we add these term to the new review annotation
     * @return
     */
    List<Term> termsForReview() {
        terms().unique()
    }

    /**
     * Check if its an algo annotation
     */
    boolean isAlgoAnnotation() {
        return false
    }

    /**
     * Check if its a review annotation
     */
    boolean isReviewedAnnotation() {
        return false
    }

    /**
     * Get CROP (annotation image area) URL for this annotation
     * @param cytomineUrl Cytomine base URL
     * @return Full CROP Url
     */
    def getCropUrl(String cytomineUrl) {
        UrlApi.getUserAnnotationCropWithAnnotationId(id)
    }

    /**
     * Get a list of each term link with annotation
     * For each term, add all users that add this term
     * [{id: x, term: y, user: [a,b,c]}, {...]
     */
    def usersIdByTerm() {
        def results = []
        if(this.version!=null) {
            AnnotationTerm.findAllByUserAnnotation(this).each { annotationTerm ->
                def map = [:]
                map.id = annotationTerm.id
                map.term = annotationTerm.term?.id
                map.user = [annotationTerm.user?.id]
                def item = results.find { it.term == annotationTerm.term?.id }
                if (!item) {
                    results << map
                } else {
                    item.user.add(annotationTerm.user.id)
                }
            }
        }
        results
    }

    /**
     * Insert JSON data into domain in param
     * @param domain Domain that must be filled
     * @param json JSON containing data
     * @return Domain with json data filled
     */
    static UserAnnotation insertDataIntoDomain(def json, def domain = new UserAnnotation()) {
        try {
            domain.id = JSONUtils.getJSONAttrLong(json,'id',null)
            domain.geometryCompression = JSONUtils.getJSONAttrDouble(json, 'geometryCompression', 0)
            domain.created = JSONUtils.getJSONAttrDate(json, 'created')
            domain.updated = JSONUtils.getJSONAttrDate(json, 'updated')
            domain.location = new WKTReader().read(json.location)
            domain.image = JSONUtils.getJSONAttrDomain(json, "image", new ImageInstance(), true)
            //domain.imageId = Long.parseLong(json["image"].toString())
            domain.project = JSONUtils.getJSONAttrDomain(json, "project", new Project(), true)
            domain.user = JSONUtils.getJSONAttrDomain(json, "user", new SecUser(), true)

            if (!domain.location) {
                throw new WrongArgumentException("Geo is null: 0 points")
            }
            if (domain.location.getNumPoints() < 1) {
                throw new WrongArgumentException("Geometry is empty:" + domain.location.getNumPoints() + " points")
            }
        } catch (com.vividsolutions.jts.io.ParseException ex) {
            throw new WrongArgumentException(ex.toString())
        }
        return domain;
    }

    /**
     * Define fields available for JSON response
     * This Method is called during application start
     */
    static void registerMarshaller() {
        Logger.getLogger(this).info("Register custom JSON renderer for " + UserAnnotation.class)
        JSON.registerObjectMarshaller(UserAnnotation) { annotation ->
            def returnArray = [:]
            ImageInstance imageinstance = annotation.image
            returnArray['class'] = annotation.class
            returnArray['id'] = annotation.id
            returnArray['location'] = annotation.location.toString()
            returnArray['image'] = annotation.image?.id
            returnArray['geometryCompression'] = annotation.geometryCompression
            returnArray['project'] = annotation.project.id
            returnArray['container'] = annotation.project.id
            returnArray['user'] = annotation.user?.id
            returnArray['nbComments'] = annotation.countComments
            returnArray['area'] = annotation.area
            returnArray['perimeterUnit'] = annotation.retrievePerimeterUnit()
            returnArray['areaUnit'] = annotation.retrieveAreaUnit()
            returnArray['perimeter'] = annotation.perimeter
            returnArray['centroid'] = annotation.getCentroid()
            returnArray['created'] = annotation.created ? annotation.created.time.toString() : null
            returnArray['updated'] = annotation.updated ? annotation.updated.time.toString() : null
            returnArray['term'] = annotation.termsId()
            returnArray['userByTerm'] = annotation.usersIdByTerm()
            returnArray['similarity'] = annotation.similarity
            returnArray['rate'] = annotation.rate
            returnArray['idTerm'] = annotation.idTerm
            returnArray['idExpectedTerm'] = annotation.idExpectedTerm
            returnArray['cropURL'] = UrlApi.getUserAnnotationCropWithAnnotationId(annotation.id)
            returnArray['smallCropURL'] = UrlApi.getUserAnnotationCropWithAnnotationIdWithMaxWithOrHeight(annotation.id, 256)
            returnArray['url'] = UrlApi.getUserAnnotationCropWithAnnotationId(annotation.id)
            returnArray['imageURL'] = UrlApi.getAnnotationURL(imageinstance.project?.id, imageinstance.id, annotation.id)
            returnArray['reviewed'] = annotation.hasReviewedAnnotation()
            return returnArray
        }
    }

    /**
     * Return domain user (annotation user, image user...)
     * By default, a domain has no user.
     * You need to override userDomainCreator() in domain class
     * @return Domain user
     */
    public SecUser userDomainCreator() {
        return user;
    }
}
