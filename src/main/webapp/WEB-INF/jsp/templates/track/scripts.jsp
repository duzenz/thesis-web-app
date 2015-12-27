<script type="text/javascript" src="${rootURL}resources/jquery/jquery-1.10.2.js"></script>
<script type="text/javascript" src="${rootURL}resources/jqueryui/jquery-ui.min.js"></script>
<script type="text/javascript" src="${rootURL}resources/primeui/primeui-1.1-min.js"></script>
<script type="text/javascript" src="${rootURL}resources/jquery/jquery.class.js"></script>
<script type="text/javascript" src="${rootURL}resources/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript" src="${rootURL}resources/datatable/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="${rootURL}resources/datatable/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="${rootURL}resources/js/Constant.js"></script>
<script type="text/javascript" src="${rootURL}resources/js/views/BaseView.js"></script>
<script type="text/javascript" src="${rootURL}resources/js/models/BaseModel.js"></script>
<script type="text/javascript" src="${rootURL}resources/js/views/TrackView.js"></script>
<script type="text/javascript" src="${rootURL}resources/js/models/TrackModel.js"></script>
<script type="text/javascript">
    var tags = Constant.Tags.toptags.tag;
    var html = "";
    for (var i = 0; i < tags.length; i++) {
        html += "<option value='" + tags[i].name + "'>" + tags[i].name + "</option>";
        $("#recommend_tag").html(html);
    }

    $(document).ready(function() {
        TrackView = new TrackView();
    });
</script>