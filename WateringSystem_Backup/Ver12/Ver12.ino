//************************************************
#include <SoftwareSerial.h>
#include<stdlib.h>
#include <string.h>
SoftwareSerial SIM900(2,3); // sim digital 2,3
#include "SIM900.h"
#include "sms.h"
SMSGSM sms;
char *p; //biến lệnh của người gửi
char *p1;
char *p2;
char *p3;
char *p4;
char smsbuffer[10]; //biến tin nhắn nhận
char n[20]="+841695821150"; //SĐT người gửi - chủ
char pos; //biến vị trí tin nhắn trong sim
char command;
String test;
boolean checkBluetooth=false;
String thayDoi;
//--------------------------------
int doAmMacDinh=50;//độ ẩm mặc định cần phải tưới
//biến quyen admin
boolean admin=false;
boolean bomm = false;
int count=0;
//********************************************
#define Analog 1//do am dat analog 5
#define Analog1 3 //kiem tra nuoc analog 3
//*****************************************************
#include "DHT.h"
#define DHTPIN 4 // dht digital 4
#define DHTTYPE DHT11 
#define Bom 6 // bom digital 6
#define Van 5 // van digital 5
DHT dht(DHTPIN, DHTTYPE);

//Mái che
int value;
#define Analog2 0// quang tro analog 4
#define manChe 8 // manche digital 8
//******************************************
int maiche = 0;
int demReset=0;
/*****************************************************************************SETUP MẠCH*****************************************************************************/
void setup() 
{
 Serial.begin(9600);
 dht.begin();
 pinMode(Bom,OUTPUT);
 pinMode(Van,OUTPUT);
 dong_van();
 dong_bom();
 pinMode(manChe,OUTPUT);
 Serial.println("GSM Shield testing.");
  if (gsm.begin(2400)) 
      Serial.println("\nstatus=READY");
  else Serial.println("\nstatus=IDLE"); 
}
/*****************************************************************************VÒNG LẶP TRONG MẠCH*****************************************************************************/
void loop() 
{  
  // Check he thong hoat dong
   if(demReset==0)
   {
     sms.SendSMS(n,"He thong da bat dau hoat dong...");
     demReset=1;
   } 
    while(Serial.available() > 0)
     {
       command = ((byte)Serial.read());
       test += command;
       delay(100);
   } 
    //Bật kết nối Bluetooth
     if(test=="O")
     {  
       checkBluetooth=true;
       Serial.print("O");
       test="";
       command='\0';
     }
     if(test=="F")
     {  
        checkBluetooth=false;
        Serial.print("F");
        test="";
        command='\0';
     }
     //Bluetooth bắt đầu hoạt động
   if(checkBluetooth==true)
         {
           if(test=="1")//Bật máy bơm
           {
            // Serial.println("Bat may bom");
             Serial.print("1");
              mo_bom();//bơm nước
           }
           else if(test=="2")//Tắt máy bơm
           { 
             //Serial.println("Tat may bom");
              dong_bom();//bơm nước
               Serial.print("2");
           }
           else if(test=="3")//Kéo màn lại
           {
           
             Serial.print("3");
              digitalWrite(manChe,HIGH);
           }
           else if(test=="4")//Kéo màn ra
           {
             Serial.print("4");
              digitalWrite(manChe,LOW);
           }
           else if(test=="h")//kiểm tra nếu người dùng chọn thay đổi độ ẩm
           {
             thayDoi=test;
             
           }
           else if(test=="t")
           {
             thayDoi=test;
              
           }
           else
           {
             if(test!="")
             { 
               if(thayDoi=="h")//thay đổi độ ẩm
               {    doAmMacDinh=test.toInt();
                    sms.SendSMS(n,Reply1(doAmMacDinh));
                    thayDoi="";
                    Serial.print("h");
               }
               if(thayDoi=="t")//thay đổi thời gian
                { 
                  thayDoi="";
                }
              
             }
             
           }
            test="";
           command='\0';
           Serial.print("h");
           delay(50);
           convertIntToChar(DO_AM_DAT());
           Serial.print("t");
           delay(50);
           convertIntToChar(NHIET_DO_KHONG_KHI());
             
     }
   else
   {
            //-------------------------------*Phần xử lý tin nhắn*--------------------------------
       //Đọc tin nhắn
        pos=sms.IsSMSPresent(SMS_UNREAD);
        Serial.println((int)pos);
        Serial.print("POS=");
        Serial.println((int)pos);
        smsbuffer[0]='\0';
        sms.GetSMS((int)pos,n,40,smsbuffer,160);
        if((int)pos>0)
        {
            p=strstr(smsbuffer,"STATUS");//tìm kiếm chuổi STATUS trong nội dung tin nhắn
            p1=strstr(smsbuffer,"WATERING");//thì tìm kiếm chuổi WATERING trong nội dung tin nhắn
            p2=strstr(smsbuffer,"STOP");
            p3=strstr(smsbuffer,"YES");
            p4=strstr(smsbuffer,"CHANGE");
            if(p)//nếu mà nội dung tin nhắn là STATUS
            {
               sms.SendSMS(n,Reply(DO_AM_DAT(),DO_AM_KHONG_KHI(),NHIET_DO_KHONG_KHI()));
            }
            if(p1)//Nếu nội dung là WATERING
            {
                 //Trước khi tưới phải kiểm tra xem điều kiện độ ẩm đất có hợp lý hay ko
                  if(DO_AM_DAT()>doAmMacDinh)
                  {
                     sms.SendSMS(n,Reply2());
                  }
             }
            if(p2)//Nếu nội dung là STOP
            {
                   sms.SendSMS(n,"Tuoi hoan tat.");
                   admin=false;
                   dong_bom();
                   dong_van();
             }
            if(p3)//Nếu nội dung là YES
            {
                   sms.SendSMS(n,"Dang tuoi...");
                   admin=true;
                   mo_bom();
            }
            if(p4)//Nếu nội dung là CHANGE
            {
                    int Length=sizeof(smsbuffer);//lấy độ dài của chuổi tin nhắn
                    char changeNhietDo[163]="\0";//biến để lấy ra giá trị nhiệt độ từ nội dung tin nhắn thay đổi nhiệt độ
                    strncpy(changeNhietDo,smsbuffer+7,2);//cắt giá trị nhiệt độ từ trong nội dung chuổi tin nhắn
                    doAmMacDinh=atoi(changeNhietDo);//gán doAmDatCanTuoi bằng với giá trị nhiệt độ cần thay đổi trong nội dung tin nhắn
                    sms.SendSMS(n,Reply1(doAmMacDinh));
            }
        }
       //-------------------------------------------------------------------------------------------
        
        Auto();
        Maiche();
   }

    delay(2000);
}

/**============================================================================CÁC HÀM XỬ LÝ========================================================**/
//Các hàm đóng mở
void mo_van()
{
  digitalWrite(Van,LOW);
}
void dong_van()
{
  digitalWrite(Van,HIGH);
}
void mo_bom()
{ 
  if (Check_nuoc()>=800)
  {
     mo_van();
  }   
 digitalWrite(Bom,LOW);
}
void dong_bom()
{
  digitalWrite(Bom,HIGH);
}
//Hàm reset
void Reset()
{  
    sms.SendSMS(n,"Tuoi hoan tat.");
    digitalWrite(Bom,HIGH);
    digitalWrite(Van,HIGH); 
    count=0;
    admin=false;
}

void Reset1()
{     sms.SendSMS(n,"Tuoi hoan tat.");
    digitalWrite(Bom,HIGH);
    digitalWrite(Van,HIGH); 
    count=0;
    bomm=false;
}
/*Hàm xử lý mái che**/
void Maiche()
 {
  value= analogRead(Analog2);
 
  if(value<=100)
  {
    maiche+=1;
    if (maiche==5)
    {
    digitalWrite(manChe,HIGH);
    }
    if(maiche>5) 
    {
      maiche=0;
    }
  }
  else
  {
      maiche+=1;
      if (maiche==5)
      {
      digitalWrite(manChe,LOW);
      }
       if(maiche>5) 
      {
      maiche=0;
      }
   }
 }
/*=====================================================================================*/
//Các hàm lấy thông số, giá trị
 int DO_AM_DAT()
{
 float ana=analogRead(Analog);
 float anal=ana-223;
 float analog=anal/800;
 float doAmDat=100-(analog*100);
 int test=(int)doAmDat;
  return test;
}
 int DO_AM_KHONG_KHI()
{
  int h=dht.readHumidity();
  return h;
}
 int NHIET_DO_KHONG_KHI()
{
  int t=dht.readTemperature();
  return t;
}
  int Check_nuoc()
{
  float ana=analogRead(Analog1);
  return ana;
}
/*=====================================================================================*/
//Các hàm tin nhan
char *Reply(int Doamdat,int Doamkhongkhi,int Nhietdo)
{  
  char q[10],e[10];
  char DO_AM[30]="Do am dat: ";
  char NHIET_DO[30]="Nhiet do: ";
  char result[160]="\0";
  itoa(Doamdat,q,10);//chuyển đổi doAm về chuổi DO_AM
  itoa(Nhietdo,e,10);
  strcat(result,DO_AM); //nối biến DO_AM vào sau biến result->kết quả là: "Độ ẩm: 123" 
  strcat(result,q);
  strcat(result,"%\n");//xuống dòng
  strcat(result,NHIET_DO);
  strcat(result,e);
  strcat(result,"*C");
  return result;
}
char *Reply1(int doAmMacDinh)
{  
  char q[10];
  char result[160]="\0";
  char content[50]="Do am can tuoi da thay doi thanh ";
  itoa(doAmMacDinh,q,10);
  strcat(result,content);
  strcat(result,q);
  strcat(result,"%");
  return result;
}
char *Reply2()
{  
  char content[80]="Do am dat da du.De tiep tuc tuoi,soan YES";
  return content;
}
/*=====================================================================================*/
/**Phần xử lý việc bơm nước**/
void Auto ()
{
    if (admin ==true)
    {
      count+=1;
      if(count==10)
      {
        Reset();
      } 
    }
    else
   { //Phần tưới tự động
    if (bomm ==false)
    {
      if(DO_AM_DAT()<=doAmMacDinh)//mỗi lần muốn tưới phải kiểm tra xem độ ẩm có lớn hơn 50% chưa(tức là đất phải khô mới tưới)
       {   
           bomm=true;
           sms.SendSMS(n,"Dang tuoi...");
           mo_bom();
       }
    }
    else
    {
      count+=1;
      if(count==10)
      {
        Reset1();
      } 
    }
   }
}  
 //Hàm chuyển đổi số nguyên sang char và lấy độ dài  
void convertIntToChar(int number)
{
    char NUMBER[2];
    itoa(number,NUMBER,10);
  //Gửi thông tin chuổi số đi
  for(int i=0;i<sizeof(NUMBER);i++)
    {
          
               Serial.print(NUMBER[i]);  
              delay(50); 
           
     }
}
//-----------------------------------------------------------------------------------------------

