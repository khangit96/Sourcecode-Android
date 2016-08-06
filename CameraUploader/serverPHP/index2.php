<?php
header('Content-Type: text/html; charset=utf-8');
echo "Lấy Hình Từ Android";
error_reporting(E_ALL);
$target_Path = "images/";
if(isset($_POST['ImageName'])){
$imgname = $_POST['ImageName'];
$target_Path = $target_Path.$imgname;
$imsrc = base64_decode($_POST['base64']);
$fp = fopen($target_Path, 'w');
fwrite($fp, $imsrc);
if(fclose($fp)){
	echo "Tải hình thành công";
}else{
	echo "Tải hình thất bại";
}
}
?>