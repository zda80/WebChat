'use strict';

function validate() {

    if (document.getElementById('username').value == "" && document.getElementById('password').value == "") {
        alert("Enter Username and password");
        return false;
    }
    if (document.getElementById('username').value == "") {
        alert("Enter Username");
        return false;
    }
    if (document.getElementById('password').value == "") {
        alert("Enter Password");
        return false;
    }
}
