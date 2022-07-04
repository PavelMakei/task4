function preventBack() {
     // window.alert("function preventBack started")
    window.history.forward();
}

setTimeout("preventBack()", 0);
window.onunload = function () {
    null
};
history.pushState(null, null, document.URL);


function validateForms(forms) {
     // window.alert('function validateForm started')
    'use strict'
    // var forms = document.querySelectorAll('.needs-validation')
    Array.prototype.slice.call(forms)
        .forEach(function (form) {
            form.addEventListener('submit', function (event) {
                if (!form.checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                }
                form.classList.add('was-validated')
            }, false)
        })
}

