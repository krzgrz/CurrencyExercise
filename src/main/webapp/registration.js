
function submitRegistration (endpoint, successUrl) {
    if (confirm('Create account?'/* + endpoint*/)) {
        formData = {}
        $.each($("#registrationForm").serializeArray(), function (i,v) {
            formData[v.name] = v.value;
        });
        $.ajax({
            method:'POST',
            data: JSON.stringify(formData),//$("#registrationForm").serializeObject(),
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

function submitExchangeTransaction (direction, successUrl) {
    if (confirm('Order transaction?'/* + endpoint*/)) {
        formData = {}
        if (direction === 'right') {
            endpoint = $("#btRight").attr("data-endpoint");
            formData['currencySold'] = 'USD';
            formData['currencyBought'] = 'PLN';
            if ($("#amount1").val()) {
                formData['amountSold'] = Math.abs($("#amount1").val());
            }
            if ($("#amount2").val()) {
                formData['amountBought'] = Math.abs($("#amount2").val());
            }
        } else if (direction === 'left') {
            endpoint = $("#btLeft").attr("data-endpoint");
            formData['currencySold'] = 'PLN';
            formData['currencyBought'] = 'USD';
            if ($("#amount2").val()) {
                formData['amountSold'] = Math.abs($("#amount2").val());
            }
            if ($("#amount1").val()) {
                formData['amountBought'] = Math.abs($("#amount1").val());
            }
        }
//        $.each($("#registrationForm").serializeArray(), function (i,v) {
//            formData[v.name] = v.value;
//        });
        $.ajax({
            method:'POST',
            data: JSON.stringify(formData),
//            dataType:"json",
            contentType:"application/json; charset=utf-8",
            url: endpoint,
            success: function (response) {
                alert('Transaction ordered: ' + response);
                if (successUrl === '') {
                    window.location.reload(true);
                } else {
                    window.location.href = successUrl;
                }
            },
            error: function (xhr) {
                alert('Unable to order transaction: ' + xhr.status);
            }
        });
    }
}