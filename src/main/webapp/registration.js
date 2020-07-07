
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

function submitExchangeTransaction (direction, endpoint, successUrl) {
    if (confirm('Order transaction?'/* + endpoint*/)) {
        formData = {}
        if (direction === 'right') {
            formData['currencySold'] = 'USD';
            formData['currencyBought'] = 'PLN';
            formData['amountSold'] = $("#amount1").val();
            formData['amountBought'] = $("#amount2").val();
        } else if (direction === 'left') {
            formData['currencySold'] = 'PLN';
            formData['currencyBought'] = 'USD';
            formData['amountSold'] = $("#amount2").val();
            formData['amountBought'] = $("#amount1").val();
        }
//        $.each($("#registrationForm").serializeArray(), function (i,v) {
//            formData[v.name] = v.value;
//        });
        $.ajax({
            method:'POST',
            data: JSON.stringify(formData),
//            dataType:"json",
            contentType:"application/json; charset=utf-8",
            url: endpoint + '/' + $("#pesel").text(),
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