
function submitRegistration (endpoint, successUrl) {
    if (confirm('Create account?'/* + endpoint*/)) {
        json = {}
        $.each($("#registrationForm").serializeArray(), function (i,v) {
            json[v.name] = v.value;
        });
        $.ajax({
            method:'POST',
            data: JSON.stringify(json),//$("#registrationForm").serializeObject(),
//            dataType:"json",
            contentType:"application/json; charset=utf-8",
            url: endpoint,
            success: function (response) {
                alert('Account created: ' + response);
                if (successUrl === '') {
                    window.location.reload(true);
                } else {
                    window.location.href = successUrl;
                }
            },
            error: function (xhr) {
                alert('Unable to create account: ' + xhr.status);
            }
        });
    }
}
