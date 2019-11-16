$(document).ready(function () {

    initDatePickers();

    callEndpoint("http://127.0.0.1:8080/contacts-app/contacts", "POST", {}, refreshContactList);

    initContactsList();

});

function initContactsList() {
    var $body = $('body');

    $body.on('click', 'div.master_list div.list-group button', function () {
        var $button = $(this),
            article_option = $button.attr('data-option'),
            article_selector = 'article.' + article_option,
            $master_detail = $button.closest('.master_detail'),
            $article = $master_detail.find(article_selector);

        $master_detail.find('article').removeClass('grow fadeIn');

        $article.addClass('grow fadeIn');
    });

    $body.on('click', 'button.delete-contact', function () {
        var $button = $(this);
        var id = $button.attr('data-id');

        callEndpoint("http://127.0.0.1:8080/contacts-app/contacts/remove", "POST", {"id": id}, function(response){
            callEndpoint("http://127.0.0.1:8080/contacts-app/contacts", "POST",  {}, refreshContactList);
        })
    });
}

function initDatePickers() {
    $('.datepicker').each(function() {
        $(this).datepicker('clearDates');
    });
}

function refreshContactList(response) {
    var contactsList = JSON.parse(response);

    $('#contacts').empty();

    callEndpoint("http://127.0.0.1:8080/contacts-app/contact-row.html", "GET", {}, function(response) {

        contactsList.forEach(function (element, index, array) {
            var row = response;
            for (var property in element) {
                if (Object.prototype.hasOwnProperty.call(element, property)) {
                    var pattern = '${'+property+'}';
                    row = row.replace(pattern, element[property]);
                }
            }
            $('#contacts').append(row);
        });
    });
}

function closeModalAndRefreshList(response){
    $('#contact-form-modal').modal('toggle');

    callEndpoint("http://127.0.0.1:8080/contacts-app/contacts", "POST", {}, refreshContactList);
}

function postForm(formElement, url, handler){
    var textInputs = $('#'+formElement+' input[type=text]');

    var payload = {};
    for (i = 0; i < textInputs.length; i++) {
        payload[textInputs[i].id]=textInputs[i].value;
    }

    callEndpoint(url,"POST", payload, handler);
}

function callEndpoint(url, method, payload, handler) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            if (handler!=null) {
                handler(this.responseText);
            }
        }
    };
    xhttp.open(method, url, true);
    xhttp.setRequestHeader("Content-Type", "application/json");
    xhttp.send(JSON.stringify(payload));
}

