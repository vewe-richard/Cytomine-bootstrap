var ProjectView = Backbone.View.extend({
       tagName : "div",
       searchProjectPanelElem : "#searchProjectPanel",
       projectListElem : "#projectlist",

       initialize: function(options) {
          this.container = options.container;
          this.model = options.model;
          this.el = options.el;
          this.searchProjectPanel = null;
          this.addProjectDialog = null;
       },
       render : function () {
          var self = this;
          require([
             "text!application/templates/project/ProjectList.tpl.html"
          ],
              function(tpl) {
                 self.doLayout(tpl);
              });

          return this;
       },
       doLayout: function(tpl) {
          console.log("ProjectView: render");

          var self = this;
          $(this.el).html(_.template(tpl, {}));

          //print search panel
          self.loadSearchProjectPanel();

          //print all project panel
          self.loadProjectsListing();

          return this;
       },
       /**
        * Refresh all project panel
        */
       refresh : function() {
          console.log("ProjectView: refresh");
          var self = this;
          //TODO: project must be filter by user?
          var idUser =  undefined;
          new ProjectCollection({user : idUser}).fetch({
                 success : function (collection, response) {
                    self.model = collection;
                    self.render();
                 }});


       },
       /**
        * Create search project panel
        */
       loadSearchProjectPanel : function() {
          console.log("ProjectView: searchProjectPanel");

          var self = this;
          //create project search panel
          self.searchProjectPanel = new ProjectSearchPanel({
                 model : self.model,
                 ontologies : window.app.models.ontologies,
                 el:$("#projectViewNorth"),
                 container : self,
                 projectsPanel : self
              }).render();
       },
       /**
        * Print all project panel
        */
       loadProjectsListing : function() {
          var self = this;
          //clear de list
          $(self.projectListElem).empty();

          //print each project panel
          self.model.each(function(project) {
             var panel = new ProjectPanelView({
                    model : project,
                    projectsPanel : self
                 }).render();

             $(self.projectListElem).append(panel.el);

          });
       },
       /**
        * Show all project from the collection and hide the other
        * @param projectsShow  Project collection
        */
       showProjects : function(projectsShow) {
          var self = this;
          self.model.each(function(project) {
             //if project is in project result list, show it
             if(projectsShow.get(project.id)!=null)

                $(self.projectListElem+project.id).show();
             else
                $(self.projectListElem+project.id).hide();
          });
       }


    });
