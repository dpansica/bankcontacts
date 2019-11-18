$(document).ready(function () {

    initDatePickers();

    callEndpoint("http://127.0.0.1:8001/contacts-app/contacts", "POST", {}, refreshContactList);

    initContactsList();

});

function loadImage(src) {
    $('#contactPicture').empty();
    var image = new Image();
    image.src = src;
    image.height = 150;
    $('#contactPicture').append(image);
}

function initContactsList() {
    var $body = $('body');

    $body.on('click', 'button.delete-contact', function () {
        var $button = $(this);
        var id = $button.attr('data-id');

        callEndpoint("http://127.0.0.1:8001/contacts-app/contacts/remove", "POST", {"id": id}, function(response){
            callEndpoint("http://127.0.0.1:8001/contacts-app/contacts", "POST",  {}, refreshContactList);
        })
    });

    $body.on('click', 'button.edit-contact', function () {

        var $button = $(this);
        var id = $button.attr('data-id');

        callEndpoint("http://127.0.0.1:8001/contacts-app/contacts/"+id, "GET", {}, function(response){

            var object = JSON.parse(response);
            for (var property in object) {
                if (Object.prototype.hasOwnProperty.call(object, property)) {
                    var selector = '#contactForm #'+property;
                    $(selector).val(object[property]);
                }
            }

            $('#contactPicture').empty();
            if (object['picture']){
                loadImage(object['picture']);
            }

            callEndpoint("http://127.0.0.1:8001/contacts-app/addresses", "POST", {"contactId": id}, function(response){

                refreshAddressList(response);

                $('#addressForm #contactId').val(object['id']);

                callEndpoint("http://127.0.0.1:8001/contacts-app/phones", "POST", {"contactId": id}, function(response) {

                    refreshPhoneList(response);

                    $('#phoneForm #phoneContactId').val(object['id']);

                    $('.initiallyHidden').addClass('sectionEditable').removeClass('initiallyHidden');

                    $('#contact-form-modal').modal('toggle');

                });
            });


        })
    });
}

function initDatePickers() {
    $('.datepicker').each(function() {
        $(this).datepicker({format: 'dd/mm/yyyy'});
    });
}

function refreshContactList(response) {
    var contactsList = JSON.parse(response);

    $('#contacts').empty();

    callEndpoint("http://127.0.0.1:8001/contacts-app/contact-row.html", "GET", {}, function(response) {

        contactsList.forEach(function (element, index, array) {
            var row = response;
            for (var property in element) {
                if (Object.prototype.hasOwnProperty.call(element, property)) {
                    var pattern = '_'+property+'_';
                    row = row.replace(new RegExp(pattern, "g"), element[property]);
                }
            }
            $('#contacts').append(row);
        });
    });

    $('.initiallyHidden').addClass('sectionEditable').removeClass('initiallyHidden');
}

function refreshAddressList(response) {
    var contactsList = JSON.parse(response);

    $('#addresses').empty();

    callEndpoint("http://127.0.0.1:8001/contacts-app/address-row.html", "GET", {}, function(response) {

        contactsList.forEach(function (element, index, array) {
            var row = response;
            for (var property in element) {
                if (Object.prototype.hasOwnProperty.call(element, property)) {
                    var pattern = '_'+property+'_';
                    row = row.replace(new RegExp(pattern, "g"), element[property]);
                }
            }
            $('#addresses').append(row);
        });

        var $body = $('body');
        $body.on('click', 'button.delete-address', function () {
            var $button = $(this);
            var id = $button.attr('data-id');

            callEndpoint("http://127.0.0.1:8001/contacts-app/address/remove", "POST", {"id": id}, function(response){
                callEndpoint("http://127.0.0.1:8001/contacts-app/addresses", "POST",  {}, refreshAddressList);
            })
        });

    });
}

function refreshPhoneList(response) {
    var phonesList = JSON.parse(response);

    $('#phones').empty();

    callEndpoint("http://127.0.0.1:8001/contacts-app/phone-row.html", "GET", {}, function(response) {

        phonesList.forEach(function (element, index, array) {
            var row = response;
            for (var property in element) {
                if (Object.prototype.hasOwnProperty.call(element, property)) {
                    var pattern = '_'+property+'_';
                    row = row.replace(new RegExp(pattern, "g"), element[property]);
                }
            }
            $('#phones').append(row);
        });

        var $body = $('body');
        $body.on('click', 'button.delete-phone', function () {
            var $button = $(this);
            var id = $button.attr('data-id');

            callEndpoint("http://127.0.0.1:8001/contacts-app/phone/remove", "POST", {"id": id}, function(response){
                callEndpoint("http://127.0.0.1:8001/contacts-app/phones", "POST",  {}, refreshPhoneList);
            })
        });

    });
}

function refreshFKidsAndRefreshContactList(response){

    var contact = JSON.parse(response);

    $('#contactId').val(contact.id);
    $('#phoneContactId').val(contact.id);

    callEndpoint("http://127.0.0.1:8001/contacts-app/contacts", "POST", {}, refreshContactList);
}


function closeModalAndRefreshContactList(response){
    $('#contact-form-modal').modal('toggle');

    callEndpoint("http://127.0.0.1:8001/contacts-app/contacts", "POST", {}, refreshContactList);
}

function closeModalAndRefreshAddressList(response){
    $('#address-form-modal').modal('toggle');

    callEndpoint("http://127.0.0.1:8001/contacts-app/addresses", "POST", {}, refreshAddressList);
}

function closeModalAndRefreshPhoneList(response){
    $('#phone-form-modal').modal('toggle');

    callEndpoint("http://127.0.0.1:8001/contacts-app/phones", "POST", {}, refreshPhoneList);
}

function writeBase64To(file, destinationId, handler) {
    var reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = function () {
        $('#'+destinationId).val(reader.result);
        if (handler!=null){
            handler(reader.result);
        }
        // console.log(reader.result);
    };
    reader.onerror = function (error) {
        console.log('Error: ', error);
    };
}

function postForm(formElement, url, handler){
    var textInputs = $('#'+formElement+' input[type=text], #'+formElement+' input[type=hidden]');

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
    xhttp.setRequestHeader('cache-control', 'no-cache, must-revalidate, post-check=0, pre-check=0');
    xhttp.setRequestHeader('cache-control', 'max-age=0');
    xhttp.setRequestHeader('expires', '0');
    xhttp.setRequestHeader('expires', 'Tue, 01 Jan 1980 1:00:00 GMT');
    xhttp.setRequestHeader('pragma', 'no-cache');
    xhttp.send(JSON.stringify(payload));
}

