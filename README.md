<h1> Project Bd </h1>

<p> Pada suatu jaringan yang ekslusif, dimana terapat komunikasi dengan traffic data yang bersifat restic maka dibutuhkan sebuah prosedur pengamanan baik secara pengiriman data dan kerahasianya, bd Project memiliki kemapuan untuk melakukan perlindungan tersebut  terhadap ancaman-ancaman komunikasi di dalam suatu jaringan atau server enkripsi data </p>

Untuk menyediakan teknologi komunikasi internal seperti :

1. Transfer file 
2. command shell / remote command
3. encrypted chat 
4. komunikasi antara node-to-node

Cara Mengunakan :
  1. Pc sebagai Server
        <p> Compile code java bdServer, setelah dicompile akan keluar sebuah form isikan port ( Terserah tapi usahakan diatas 1000 ) karena port 1-1000 adalah port yang telah mempunyai service standard, Setelah itu klik start </p> 
  2. Pc sebagai Client 
        <p> Compile code java bdClient, setelah dicompile akan keluar sebuah form, isikan host ( alamat ip server ) dan port ( sama dengan port listen di server ) setelah itu klik connect </p> 
  <p> setelah porses tersebut selesai anda bisa tab main, dan memilih ( Memilih dicomboxnya ) data mana yang mau dikirim. proses komunikasi 2 arah</p> 

Cara Kerja Program ini : <p> 
  <p> ada 5 buah class yaitu : </p> 
    <p> 1. BMRdb???.java : berfungsi sebagai main program yang menjalankan Gui (class bd???Gui.java ).</p> 
        <p> BMRbdServer.java -> di app server </p>
        <p> BMRbdClient.java -> di app Client </p>
    <p> 2. bd???Gui.java : berfungsi sebagai alat interaksi user yang akan mengeksekusi program ( class SProcess.java ) </p> 
        <p> bdServerGui.java -> di app server </p> 
        <p> bdClientGui.java -> di app Client </p> 
    <p> 3. SProcess.java : berfungsi sebagai libaray pokok yang digunkan untuk mengatur komunikasi data. </p> 
    <p> Contoh menerima bagaimana byte data itu Di ( terima, kirim, eksekusi, enkripsi, dekripsi, olah ). bagaimana bisa komunikasi berjalan 2 arah dengan dibuat di Thread ( process kecil dari process induknya ). bagaimana bisa mengolah Input / Output baik itu di network atau read write file lognya. sebagian proses di class ini dibantu oleh class ConV.java dan Crypto.java untuk melakukan </p> 
    <p> 4. ConV.java     : berfungsi sebagai libaray yang mengconversi data ( File, String, Integer ) ke Byte  <p>
    <p> 5. Cryoto.java   : berfungsi sebagai libaray yang mengenkripsi dan dekripsi byte. Di bd Project menggunakan algoritma  Rsa <p>
</p>
Contoh cara kerja Transfer File dari server ke client
-> file input A                                                     file input A
-> membagi ukuran file menjadi 100 byte                             penggabungan file-file yang telah dipecah
-> enkripsi Rsa (public key client)                                 dekripsi ( private key server )
-> bd project membuka socket (server)                               bd project membuka socket client
------------------------------------- lewat jaringan (wlan / eth / ppp )----------------------------------------------
