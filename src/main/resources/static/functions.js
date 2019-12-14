 uF = main.getElementById("userForm");
 uT = document.getElementById("tracksForm");

function generateUserURlFun(){
    var userInput = document.getElementById('user_id').value;
    var urlLink = "http://localhost:8080/users/";
    urlLink = urlLink + userInput;
    uF.action = urlLink;
}

function generateTracksURlFun(){
    var userInput = document.getElementById('track_id').value;
    var urlLink = "http://localhost:8080/tracks/";
    urlLink = urlLink + userInput;
    uT.action = urlLink;
}