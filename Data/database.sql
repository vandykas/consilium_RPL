DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

--basic database
CREATE TABLE Pengguna (
	idPengguna char(6) primary key not null,
	nama varchar (60) not null,
	password varchar (20) not  null,
	email varchar (60) not null
);
	
CREATE TABLE DosenPembimbing (
	idDosen char(6) primary key not null,
	pernahLogin boolean,
	foreign key (idDosen) references Pengguna (idPengguna)
);

CREATE TABLE Admin (
	idAdmin char(6) primary key not null,
	foreign key (idAdmin) references Pengguna (idPengguna)
);

CREATE TABLE Ruangan (
	nomorRuangan int primary key not null,
	namaRuangan varchar(50) not null,
	statusRuangan boolean not null,
	jenisRuangan boolean not null
);

CREATE TABLE Topik (
	kodeTopik varchar(10) primary key not null,
	judulTopik varchar (200) not null
);

CREATE TABLE Mahasiswa (
	idMahasiswa char(6) primary key not null,
	pernahLogin boolean,
	foreign key (idMahasiswa) references Pengguna (idPengguna),
	sebelumUTS int not null,
	setelahUTS int not null,
	--menambahkan foreign key kode topik untuk mahasiswa
	kodeTopik varchar(10) not null,
	FOREIGN KEY (kodeTopik) REFERENCES Topik(kodeTopik)
);

CREATE TABLE Jadwal (
    idJadwal SERIAL PRIMARY KEY,
    hari VARCHAR(6) NOT NULL,
    tanggal DATE NOT NULL, --(yyyy-mm-dd)
    jamMulai TIME NOT NULL,--(hh:mm)
    jamSelesai TIME NOT NULL,
	--menambahkan nomor ruangan ke jadwal 
	nomorRuangan int not null,
	FOREIGN KEY (nomorRuangan) REFERENCES Ruangan(nomorRuangan)
);

CREATE TABLE Kuliah (
    idJadwal INT PRIMARY KEY,
    FOREIGN KEY (idJadwal) REFERENCES Jadwal(idJadwal)
        ON DELETE CASCADE
);


CREATE TABLE Bimbingan (
    idJadwal INT PRIMARY KEY,
    tugas VARCHAR(250) ,
    inti VARCHAR(250) ,
    kelompokPerulangan int,

    FOREIGN KEY (idJadwal) REFERENCES Jadwal(idJadwal)
        ON DELETE CASCADE
);


CREATE TABLE Notifikasi (
    idNotifikasi SERIAL PRIMARY KEY ,
    statusPersetujuan BOOLEAN,
    alasanPenolakan VARCHAR(200),
    waktuKirim TIME, --hh:mm:ss
	tanggalKirim DATE, --yyyy:mm:dd
	idJadwal int not null,

	--foreign key 1:1 ke bimbingan
	FOREIGN KEY (idJadwal) REFERENCES Bimbingan(idJadwal)
	
);


--relasi 


--many to many

CREATE TABLE KuliahMaha (
    idMaha char(6) NOT NULL,
    idJadwal INT NOT NULL,

    PRIMARY KEY (idMaha, idJadwal),

    FOREIGN KEY (idMaha) REFERENCES Mahasiswa(idMahasiswa),
    FOREIGN KEY (idJadwal) REFERENCES Kuliah(idJadwal)
);

CREATE TABLE KuliahDosen(
	idDosen char(6) NOT NULL,
    idJadwal INT NOT NULL,

    PRIMARY KEY (idDosen, idJadwal),

    FOREIGN KEY (idDosen) REFERENCES DosenPembimbing(idDosen),
    FOREIGN KEY (idJadwal) REFERENCES Kuliah(idJadwal)
);

CREATE TABLE MembukaTopik (
    idDosen CHAR(6) NOT NULL,
    kodeTopik VARCHAR(10) NOT NULL,

    PRIMARY KEY (idDosen, kodeTopik),

    FOREIGN KEY (idDosen) REFERENCES DosenPembimbing(idDosen),
    FOREIGN KEY (kodeTopik) REFERENCES Topik(kodeTopik)
);

CREATE TABLE DosToStud (
	idDosen char (6) not null,
	idMahasiswa char (6) not null,

	PRIMARY KEY (idDosen, idMahasiswa),

    FOREIGN KEY (idDosen) REFERENCES DosenPembimbing(idDosen),
    FOREIGN KEY (idMahasiswa) REFERENCES Mahasiswa(idMahasiswa)
	
);

--3-ary
CREATE TABLE Melakukan (
    idDosen CHAR(6) NOT NULL,
    idMahasiswa CHAR(6) NOT NULL,
    idJadwal INT NOT NULL ,

    PRIMARY KEY (idDosen, idMahasiswa, idJadwal),

    FOREIGN KEY (idDosen) REFERENCES DosenPembimbing(idDosen),
    FOREIGN KEY (idMahasiswa) REFERENCES Mahasiswa(idMahasiswa),
    FOREIGN KEY (idJadwal) REFERENCES Bimbingan(idJadwal)
);

CREATE TABLE KirimDanTerima (
    idNotifikasi INT NOT NULL,
    idPengirim CHAR(6) NOT NULL,
    idPenerima CHAR(6) NOT NULL,

    PRIMARY KEY (idPengirim, idPenerima , idNotifikasi),

    FOREIGN KEY (idNotifikasi) REFERENCES Notifikasi(idNotifikasi),
    FOREIGN KEY (idPengirim) REFERENCES Pengguna(idPengguna),
    FOREIGN KEY (idPenerima) REFERENCES Pengguna(idPengguna)
);

---Tabel View---
CREATE OR REPLACE VIEW ViewBimbinganLengkap AS
SELECT 
	ROW_NUMBER() OVER (
        PARTITION BY m.idMahasiswa
        ORDER BY j.tanggal
    ) AS nomorBimbingan,
    m.idDosen,
    m.idMahasiswa,
    m.idJadwal,

    -- dari Bimbingan
    b.tugas,
    b.inti,
    b.kelompokPerulangan,

    -- dari Jadwal
    j.hari,
    j.tanggal,
    j.jamMulai,
    j.jamSelesai,

    -- dari Ruangan
    r.namaRuangan,
    -- dari Notifikasi
    n.statusPersetujuan

FROM Melakukan m
JOIN Bimbingan b
    ON m.idJadwal = b.idJadwal
JOIN Jadwal j
    ON b.idJadwal = j.idJadwal
JOIN Ruangan r
    ON j.nomorRuangan = r.nomorRuangan
LEFT JOIN Notifikasi n      -- gunakan LEFT JOIN karena notifikasi tidak selalu ada
    ON b.idJadwal = n.idJadwal;

----------------------------Insert data dummy----------------------------------------
INSERT INTO Pengguna VALUES
('D12135','Raymond Chandra Putra','UnparDosen123','raymond.chandra@unpar.ac.id'),
('D09005','Vania Natali','DosenUnpar321','vania.natali@unpar.ac.id'),
('M23023','Vandyka Suryadi','KecapABC','6182301023@student.unpar.ac.id'),
('M23031','Alexander Constantijn','KecapABC','6182301031@student.unpar.ac.id'),
('M23075','Keane Edbert Candra','KecapABC','6182301075@student.unpar.ac.id'),
('M23079','Alfonsus Nugraha Adi Prakosa','KecapABC','6182301079@student.unpar.ac.id'),
('M23084','Justin Farrel Kristianto','KecapABC','6182301084@student.unpar.ac.id'),
('A23001','Kenneth Nathanael','Adminboy','Kenken@unpar.ac.id'),
('A23002','Gregorius Jason Maresi','Adminboy','Greg@unpar.ac.id'),
('A23003','Andrew Kevin Alexander','Adminboy','Endru@.unpar.ac.id');

INSERT INTO DosenPembimbing VALUES
('D12135',true),
('D09005',false);

INSERT INTO Admin VALUES
('A23001'),
('A23002'),
('A23003');

INSERT INTO Topik VALUES
('RCP6001ACS','Spatial Crowdsourcing Platform untuk Kondisi Jalan'),
('RCP6002ACS','Pembangunan SI Pelatihan Admin'),
('RCP6003BCS','Pembangunan Antarmuka Aplikasi Penjadwalan Shift Admin'),
('RCP6004BCS','Eksplorasi Pengujian Perangkat Lunak Menggunakan sinon.js'),
('RCP6005CCS','Aplikasi Penghitung Keuntungan Investasi'),
('VAN6001CCS','Aplikasi Pengaturan Jadwal UTS Tugas Akhir 1'),
('VAN6002BCS','SI Pengelolaan Kegiatan Keuskupan Bandung'),
('VAN6003ACS','Aplikasi Penyusunan Jadwal Petugas Misa dengan Algoritma Genetik'),
('VAN6004CDS','Aplikasi Dashboard Interaktif untuk Menampilkan Ringkasan Data Umat per Satuan Wilayah di Keuskupan Bandung'),
('VAN6005BDS','Pengembangan Sistem Intelijen Bisnis untuk Penilaian Kinerja Bidang Pastoral di Keuskupan Bandung');

INSERT INTO Mahasiswa VALUES
('M23023',true,2, 2, 'RCP6001ACS'),
('M23031',true,2, 1, 'RCP6002ACS'),
('M23075',false,2, 1, 'VAN6005BDS'),
('M23079',false,2, 0, 'VAN6004CDS'),
('M23084',false,2, 0, 'RCP6003BCS');

INSERT INTO Ruangan VALUES
(9001,'Ruang Skripsi',true,false),
(9013,'Lab OwnGames',false,false),
(9014,'Lab Fisika',false,false),
(9015,'Lab 4',False,true),
(9016,'Lab 3',False,true),
(9017,'Lab 2',True,true),
(9018,'Lab 1',False,false),
(9120,'Kelas 9120',True,false),
(9121,'Kelas 9121',False,false),
(10111,'Kelas 10111',False,false);

INSERT INTO Jadwal (hari, tanggal, jamMulai, jamSelesai, nomorRuangan) VALUES
('Senin','2025-09-10','08:00','10:00',9015),--1
('Senin','2025-09-10','10:00','12:00',9016),--2
('Senin','2025-09-10','13:00','15:00',9015),--3
('Senin','2025-09-10','16:00','18:00',9018),--4
('Selasa','2025-10-11','08:00','10:00',9015),--5
('Selasa','2025-10-11','10:00','12:00',9017),--6
('Selasa','2025-10-11','13:00','15:00',9016),--7
('Selasa','2025-10-11','16:00','18:00',9017),--8
('Rabu','2025-11-12','08:00','10:00',10111),--9
('Rabu','2025-11-12','10:00','12:00',9018),--10
('Rabu','2025-11-12','13:00','15:00',10111),--11
('Rabu','2025-11-12','16:00','18:00',9018),--12
('Kamis','2025-12-13','08:00','10:00',9120),--13
('Kamis','2025-12-13','10:00','12:00',9121),--14
('Kamis','2025-12-13','13:00','15:00',9018),--15
('Kamis','2025-12-13','16:00','18:00',9018),--16
('Jumat','2025-09-14','08:00','10:00',9121),--17
('Jumat','2025-09-14','10:00','12:00',9018),--18
('Jumat','2025-09-14','13:00','15:00',9121),--19
('Jumat','2025-09-14','16:00','18:00',9018);--20


INSERT INTO MembukaTopik VALUES
('D12135','RCP6001ACS'),
('D12135','RCP6002ACS'),
('D12135','RCP6003BCS'),
('D12135','RCP6004BCS'),
('D12135','RCP6005CCS'),
('D09005','VAN6001CCS'),
('D09005','VAN6002BCS'),
('D09005','VAN6003ACS'),
('D09005','VAN6004CDS'),
('D09005','VAN6005BDS');

INSERT INTO Kuliah VALUES
(1),(3),(4),(7),(9),(11),(14),(17),(19),(20);

INSERT INTO Bimbingan VALUES
(2,'Membuat database dengan data dummy','Pembuatan database',1),--senin 10-12
(5,'Memperbaiki algoritma pencarian','Perbaikan algoritma A*',3),--selase 8-10
(6,'Memperbaiki fitur-fitur','Perbaikan fitur filter berdasarkan nama',null),--selasa 10-12
(8,'Menghilangkan redundant dalam kode','Menghapus looping dalam kode',4),--selasa 16-18
(10,'Membuat algoritma pencarian baru','Membuat algoritma BFS',null),-- rabu 10-12
(12,'Memperbaiki bug','Membuat algoritma BFS',3),-- rabu 16-18
(13,'Menerapkan cookies','Membuat algoritma BFS',4),-- kamis 8-10
(15,'Menghilangkan kode redundant','Membuat algoritma BFS',null),-- kamis 13-15
(16,NULL,NULL,NULL),-- kamis 16-18
(18,NULL,NULL,5);-- jumat 10-12



INSERT INTO Notifikasi (statusPersetujuan, alasanPenolakan, waktuKirim, tanggalKirim, idJadwal) VALUES
(false,'Belum lengkap', '09:00','2025-09-10',2), --1
(true,NULL,'12:00','2025-10-11',5), --2
(null,NULL,'13:00','2025-10-11',6), --3
(true,NULL,'15:00','2025-10-11',8), --4
(false,'Gagal dijadwalkan','17:00','2025-11-12',10), --5
(true,NULL,'13:00','2025-11-12',12), --6
(false,'Dosen sedang keluar kota','08:00','2025-12-13',13), --7
(false,'Dosen sakit','12:00','2025-12-13',15), --8
(false,'Mahasiswa Sakit','09:00','2025-12-13',16), --9
(True,NULL,'11:00','2025-09-14',18); --10

INSERT INTO KuliahMaha VALUES
('M23031',1),
('M23031',17),
('M23023',3),
('M23075',4),
('M23075',19),
('M23079',11),
('M23084',14),
('M23084',20);

INSERT INTO KuliahDosen VALUES
('D12135',7),
('D09005',9);

INSERT INTO Melakukan VALUES
('D12135','M23023',5),
('D12135','M23031',6),
('D12135','M23075',8),
('D09005','M23079',12),
('D09005','M23084',18);

INSERT INTO KirimDanTerima VALUES
(1,'D12135','M23023'),--2
(2,'D12135','M23023'),--5
(3,'D12135','M23023'),--6
(4,'D09005','M23023'),--8
(5,'D09005','M23023'),--10
(6,'D12135','M23023'),--12
(7,'M23031','D12135'),--13
(8,'M23075','D09005'),--15
(9,'D09005','M23023'),--16
(10,'D09005','M23023');--18


insert into DosToStud values
('D12135','M23023'),
('D09005','M23023'),
('D12135','M23031'),
('D09005','M23031'),
('D12135','M23084'),
('D09005','M23075'),
('D09005','M23079');





