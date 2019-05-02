<?php
//// Fetching Values From URL
$moduleID = $_POST['module-id'];
$examDate = $_POST['exam-date'];
$examDeadline = $_POST['exam-deadline'];
$examStart = $_POST['exam-start'];
$examEnd = $_POST['exam-end'];
$connection = mysqli_connect("localhost", "root", "Hikari@123", "examreg", "3306");
if($connection->connect_error){
    die("Connection failed: " .$connection->connect_error);
}
if(isset($_POST['module-id'])){
    $query = mysqli_query();
}
