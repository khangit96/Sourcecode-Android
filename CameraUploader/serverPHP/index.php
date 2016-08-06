<?php
 $url1=$_SERVER['REQUEST_URI'];
 header("Refresh: 5; URL=$url1");
$imagesDir = 'images/';
$images = glob($imagesDir . '*.{jpg,jpeg,png,gif}', GLOB_BRACE);
foreach ($images as $img) {
    echo "<img src='$img' width='300' height='300'/> ";
}
?>