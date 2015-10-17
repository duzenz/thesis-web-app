<script type="text/javascript" src="${rootURL}resources/jquery/jquery-1.10.2.js"></script>
<script type="text/javascript" src="${rootURL}resources/jqueryui/jquery-ui.min.js"></script>
<script type="text/javascript" src="${rootURL}resources/primeui/primeui-1.1-min.js"></script>
<script type="text/javascript" src="${rootURL}resources/jquery/jquery.class.js"></script>
<script type="text/javascript" src="${rootURL}resources/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript" src="${rootURL}resources/js/Constant.js"></script>
<script type="text/javascript" src="${rootURL}resources/js/views/BaseView.js"></script>
<script type="text/javascript" src="${rootURL}resources/js/models/BaseModel.js"></script>
<script type="text/javascript" src="${rootURL}resources/js/views/RegisterView.js"></script>
<script type="text/javascript" src="${rootURL}resources/js/models/RegisterModel.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#register_age').puidropdown();
		$('#register_country').puidropdown();
		$('#register_gender').puidropdown();
		RegisterView = new RegisterView();
	});
</script>