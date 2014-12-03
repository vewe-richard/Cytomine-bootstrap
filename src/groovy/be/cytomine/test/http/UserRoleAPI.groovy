package be.cytomine.test.http

import be.cytomine.test.HttpClient
import be.cytomine.test.Infos

/**
 * User: lrollus
 * Date: 6/12/11
 * GIGA-ULg
 * This class implement all method to easily get/create/update/delete/manage User role to Cytomine with HTTP request during functional test
 */
class UserRoleAPI extends DomainAPI {

    static def show(Long idUser, Long idRole, String username, String password) {
        String URL = Infos.CYTOMINEURL + "api/user/${idUser}/role/${idRole}.json"
        return doGET(URL, username, password)
    }

    static def show(Long idRole, String username, String password) {
        String URL = Infos.CYTOMINEURL + "api/role/${idRole}.json"
        return doGET(URL, username, password)
    }

    static def listRole(String username, String password) {
        String URL = Infos.CYTOMINEURL + "api/role.json"
        return doGET(URL, username, password)
    }

    static def listByUser(Long id,String username, String password) {
        String URL = Infos.CYTOMINEURL + "api/user/$id/role.json"
        return doGET(URL, username, password)
    }

    static def create(Long idUser, Long idRole, def json, String username, String password) {
        String URL = Infos.CYTOMINEURL + "api/user/${idUser}/role.json"
        def result = doPOST(URL,json,username,password)
        return result
    }

    static def delete(Long idUser, Long idRole, String username, String password) {
        String URL = Infos.CYTOMINEURL + "api/user/${idUser}/role/${idRole}.json"
        return doDELETE(URL,username,password)
    }

    static def define(Long idUser, Long idRole, String username, String password) {
        String URL = Infos.CYTOMINEURL + "api/user/${idUser}/role/${idRole}/define.json"
        return doPUT(URL,"",username,password)
    }


    static def openAdminSession(String username, String password, HttpClient client = null) {
        String URL = Infos.CYTOMINEURL + "session/admin/open.json"
        return doGET(URL, username, password,client)
    }

    static def closeAdminSession(String username, String password, HttpClient client = null) {
        String URL = Infos.CYTOMINEURL + "session/admin/close.json"
        return doGET(URL, username, password,client)
    }

    static def infoAdminSession(String username, String password, HttpClient client = null) {
        String URL = Infos.CYTOMINEURL + "session/admin/info.json"
        return doGET(URL, username, password,client)
    }


    static def buildToken(String user, Double validity, String username, String password) {
        String URL = Infos.CYTOMINEURL + "login/buildToken.json?username=$user&validity=$validity"
        return doPOST(URL, "",username, password)
    }

    static def showCurrentUserWithToken(String user, String token, String username, String password) {
        String URL = Infos.CYTOMINEURL + "api/user/current.json?username=$user&tokenKey=$token"
        return doGET(URL,username, password)
    }

}