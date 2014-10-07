<h1> Project Bd </h1>

Pada suatu jaringan yang ekslusif, dimana terapat komunikasi dengan traffic data yang bersifat restic maka dibutuhkan sebuah prosedur pengamanan baik secara pengiriman data dan kerahasianya, bd Project memiliki kemapuan untuk melakukan perlindungan tersebut  terhadap ancaman-ancaman komunikasi di dalam suatu jaringan atau server enkripsi data

Untuk menyediakan teknologi komunikasi internal seperti :

1. Transfer file 
2. command shell / remote command
3. encrypted chat 
4. komunikasi antara node-to-node

Cara Mengunakan :
  1. Pc sebagai Server
        Compile code java bdServer, setelah dicompile akan keluar sebuah form isikan port ( Terserah tapi usahakan diatas 1000 ) karena port 1-1000 adalah port yang telah mempunyai service standard
  2. Pc sebagai Client 
        Compile code java bdClient, setelah dicompile akan keluar sebuah form, isikan host ( alamat ip server ) dan port ( sama dengan port listen di server )
  setelah porses tersebut selesai anda bisa tab main, dan memilih ( Memilih dicomboxnya ) data mana yang mau dikirim. proses komunikasi 2 arah

Cara Kerja Program ini :
  ada 5 buah class yaitu 
    1. BMRdb???.java : berfungsi sebagai main program yang menjalankan Gui (class bd???Gui.java ). 
        BMRbdServer.java -> di app server
        BMRbdClient.java -> di app Client
    2. bd???Gui.java : berfungsi sebagai alat interaksi user yang akan mengeksekusi program ( class SProcess.java )
        bdServerGui.java -> di app server
        bdClientGui.java -> di app Client
    3. SProcess.java : berfungsi sebagai libaray pokok yang digunkan untuk mengatur komunikasi data. Contoh menerima bagaimana byte data itu Di ( terima, kirim, eksekusi, enkripsi, dekripsi, olah ). bagaimana bisa komunikasi berjalan 2 arah dengan dibuat di Thread ( process kecil dari process induknya ). bagaimana bisa mengolah Input / Output baik itu di network atau read write file lognya. sebagian proses di class ini dibantu oleh class ConV.java dan Crypto.java untuk melakukan 
    4. ConV.java     : berfungsi sebagai libaray yang mengconversi data ( File, String, Integer ) ke Byte 
    5. Cryoto.java   : berfungsi sebagai libaray yang mengenkripsi dan dekripsi byte. Di bd Project menggunakan algoritma Rsa
