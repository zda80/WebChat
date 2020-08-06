'use strict';

var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var usersArea = document.querySelector('#usersForm');

var stompClient = null;
var username = null;

connect();
messageForm.addEventListener('submit', sendMessage, true);

function connect() {
    username = document.querySelector('#username').innerText;

    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, onConnected, onError);
}

function onConnected() {
    stompClient.subscribe('/topic/chatRoom', onMessageReceived);

    var xmlhttp = new XMLHttpRequest();

    xmlhttp.open('POST', '/app/messages.oldMessages' , true);
    xmlhttp.send();
    xmlhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var myArr = JSON.parse(this.responseText);
            for(let i = 0; i < myArr.length; i++)
                printMessage(myArr[i]);

            var chatMessageDto = {
            username: username,
            type: 'JOIN'
        };
            stompClient.send("/app/chat.newUser", {},JSON.stringify(chatMessageDto));
        }
    };

    var xmlhttp2 = new XMLHttpRequest();
    xmlhttp2.open('POST', '/app/messages.usersInRoom' , false);
    xmlhttp2.send();
            var myArr = JSON.parse(xmlhttp2.responseText);
            for(let i = 0; i < myArr.length; i++)
                printUser(myArr[i]);
}

function printMessage(message){
    var messageElement = document.createElement('li');
    var dateAndUser = dateFormatter(message.date) + message.username;

    switch(message.type) {
        case 'JOIN':
            messageElement.classList.add('event-message');
            message.message = dateAndUser + ' joined to the Chat';
        break
        case 'QUIT':
            messageElement.classList.add('event-message');
            message.message = dateAndUser + ' left from the Chat';
        break
        default:
            messageElement.classList.add('chat-message');
            var usernameElement = document.createElement('strong');
            if (username == message.username)
                usernameElement.classList.add('thisUser')
            else
                usernameElement.classList.add('otherUser');
                usernameElement.appendChild(document.createTextNode(dateAndUser+ ": "));
                messageElement.appendChild(usernameElement);
        break
    }

    messageElement.appendChild(document.createTextNode(message.message));
    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}

function dateFormatter(messageDate){
    var newDate = new Date(messageDate);
return newDate.toLocaleString('en-US', { hour12: false })+" - ";
}

function onError(error) {
    messageArea.appendChild("Can't connect to the server");
}

function sendMessage(event) {
    if(messageInput.value && stompClient) {
            var chatMessageDto = {
            username : username,
            message: messageInput.value,
            type: 'CHAT'
        };
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessageDto));
        messageInput.value = '';
    }
    event.preventDefault();
}

function onMessageReceived(message) {
    var message = JSON.parse(message.body);
    printMessage(message);
      switch(message.type) {
            case 'JOIN':
                printUser(message.username);
            break
            case 'QUIT':
            var findElement =  document.getElementById(message.username);
                findElement.parentNode.removeChild(findElement);
            break
     }
}

function  printUser(username){
    var messageElement = document.createElement('li');
    messageElement.setAttribute("id", username);
    messageElement.appendChild(document.createTextNode(username));
    usersArea.appendChild(messageElement);
}