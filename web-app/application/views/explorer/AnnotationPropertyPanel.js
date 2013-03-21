var AnnotationPropertyPanel = SideBarPanel.extend({
    tagName: "div",
    keyAnnotationProperty: null,

    /**
     * ExplorerTabs constructor
     * @param options
     */
    initialize: function (options) {
        this.browseImageView = options.browseImageView;
        this.callback = options.callback;
        this.layer = options.layer;
    },
    /**
     * Grab the layout and call ask for render
     */
    render: function () {
        var self = this;
        require([
            "text!application/templates/explorer/AnnotationPropertyPanel.tpl.html"
        ], function (tpl) {
            self.doLayout(tpl);
        });
        return this;
    },

    initSelect: function (id) {
        var select = $(this.el).find("#selectLayersAnnotationProperty");
        select.empty();

        var first = _.template("<option value='<%= id %>'><%= value %></option>", { id : "selectedEmpty", value : "No Key Selected"});
        select.append(first);

        $.get("api/annotationproperty/key.json?idImage=" + id, function(data) {
            _.each (data.collection, function (item){
                var option = _.template("<option value='<%= id %>'><%= value %></option>", { id : item, value : item});
                select.append(option);
            })

            SortSelect();
        });

        var SortSelect = function sortArray(){
            var list= {};
            var el= document.getElementById('selectLayersAnnotationProperty'); //:to do use class or find another way

            for(var i=0;i<el.options.length-1;i++){
                list[i]=el.options[i+1].text;
            }
            list=list.sort();

            for(var i=0;i<el.options.length-1;i++){
                el.options[i+1].id=list[i];
                el.options[i+1].value=list[i];
                el.options[i+1].text=list[i];
            }
        }
    },

    /**
     * Render the html into the DOM element associated to the view
     * @param tpl
     */
    doLayout: function (tpl) {
        var self = this;

        var el = $('#annotationPropertyPanel'+this.model.get('id'));
        el.html(_.template(tpl, {id: this.model.get('id')}));
        var elContent = el.find(".annotationPropertyContent");
        var sourceEvent = el.find(".toggle-content");
        this.initToggle(el, elContent, sourceEvent, "annotationPropertyContent");

        self.initSelect(this.model.get('id'));

        $("#selectLayersAnnotationProperty").click(function() {
            console.log("click select");
            console.log(self.layer);
            console.log("after select");
            self.layer.loadAnnotationProperty($("#selectLayersAnnotationProperty").val());
        });
    }
});
