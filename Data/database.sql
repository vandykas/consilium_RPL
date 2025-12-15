DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

-- ==========================================================
-- 1. BASIC DATABASE
-- ==========================================================

-- Tabel Induk Utama
CREATE TABLE Pengguna (
    idPengguna char(6) primary key not null,
    nama varchar (60) not null,
    password varchar (100) not null,
    email varchar (60) not null,
    pernahLogin boolean
);


CREATE TABLE Topik (
    kodeTopik varchar(10) primary key not null,
    judulTopik varchar (200) not null
);
    
-- Tabel Anak 1: DosenPembimbing (FK ke Pengguna)
CREATE TABLE DosenPembimbing (
    idDosen char(6) primary key not null,
    foreign key (idDosen) references Pengguna (idPengguna) ON DELETE CASCADE
    -- Jika Pengguna dihapus, DosenPembimbing terkait ikut terhapus
);

-- Tabel Anak 2: Admin (FK ke Pengguna)
CREATE TABLE Admin (
    idAdmin char(6) primary key not null,
    foreign key (idAdmin) references Pengguna (idPengguna) ON DELETE CASCADE
    -- Jika Pengguna dihapus, Admin terkait ikut terhapus
);

-- Tabel Anak 3: Mahasiswa (FK ke Pengguna)
CREATE TABLE Mahasiswa (
    idMahasiswa char(6) primary key not null,
    foreign key (idMahasiswa) references Pengguna (idPengguna) ON DELETE CASCADE,
    -- Jika Pengguna dihapus, Mahasiswa terkait ikut terhapus
    
    sebelumUTS int not null,
    setelahUTS int not null,      
    
    kodeTopik varchar(10) not null,
    FOREIGN KEY (kodeTopik) REFERENCES Topik(kodeTopik)
);

-- Tabel-tabel tanpa perubahan cascade pada relasi utama
CREATE TABLE Ruangan (
    nomorRuangan int primary key not null,
    namaRuangan varchar(50) not null,
    statusRuangan boolean not null,
    jenisRuangan boolean not null
);

CREATE TABLE Jadwal (
    idJadwal SERIAL PRIMARY KEY,
    hari VARCHAR(6) NOT NULL,
    jamMulai TIME NOT NULL,
    jamSelesai TIME NOT NULL,
    nomorRuangan int not null,
    FOREIGN KEY (nomorRuangan) REFERENCES Ruangan(nomorRuangan)
);

CREATE TABLE Kuliah (
    idJadwal INT PRIMARY KEY,
    FOREIGN KEY (idJadwal) REFERENCES Jadwal(idJadwal) ON DELETE CASCADE
);


CREATE TABLE Bimbingan (
    idJadwal INT PRIMARY KEY,
    tanggal DATE NOT NULL,
    tugas VARCHAR(250) ,
    inti VARCHAR(250) ,
    kelompokPerulangan int,

    FOREIGN KEY (idJadwal) REFERENCES Jadwal(idJadwal) ON DELETE CASCADE
);


CREATE TABLE Notifikasi (
    idNotifikasi SERIAL PRIMARY KEY ,
    statusPersetujuan BOOLEAN,
    alasanPenolakan VARCHAR(200),
    waktuKirim TIME DEFAULT CURRENT_TIME,
    tanggalKirim DATE DEFAULT CURRENT_DATE,
    idJadwal int not null,

    FOREIGN KEY (idJadwal) REFERENCES Bimbingan(idJadwal) ON DELETE CASCADE 
    -- Jika Bimbingan dihapus, Notifikasi terkait ikut terhapus
);


-- ==========================================================
-- 2. RELASI MANY-TO-MANY & T-ARY (CASCAde Diperlukan)
-- ==========================================================

-- Relasi Mahasiswa/Dosen/Admin ke Pengguna (Cascade di sini)
CREATE TABLE KuliahMahaDosen (
    idPengguna char(6) NOT NULL,
    idJadwal INT NOT NULL,

    PRIMARY KEY (idPengguna, idJadwal),

    FOREIGN KEY (idPengguna) REFERENCES Pengguna(idPengguna) ON DELETE CASCADE,
    -- Jika Pengguna dihapus, entri di tabel ini ikut terhapus
    FOREIGN KEY (idJadwal) REFERENCES Kuliah(idJadwal) ON DELETE CASCADE
);

-- Relasi Dosen ke Topik (Cascade di sini)
CREATE TABLE MembukaTopik (
    idDosen CHAR(6) NOT NULL,
    kodeTopik VARCHAR(10) NOT NULL,

    PRIMARY KEY (idDosen, kodeTopik),

    FOREIGN KEY (idDosen) REFERENCES DosenPembimbing(idDosen) ON DELETE CASCADE,
    -- Jika DosenPembimbing dihapus, entri di tabel ini ikut terhapus
    FOREIGN KEY (kodeTopik) REFERENCES Topik(kodeTopik)
);

-- Relasi Dosen ke Mahasiswa (Cascade di sini)
CREATE TABLE DosToStud (
    idDosen char (6) not null,
    idMahasiswa char (6) not null,

    PRIMARY KEY (idDosen, idMahasiswa),

    FOREIGN KEY (idDosen) REFERENCES DosenPembimbing(idDosen) ON DELETE CASCADE,
    -- Jika DosenPembimbing dihapus, relasi bimbingan ikut terhapus
    FOREIGN KEY (idMahasiswa) REFERENCES Mahasiswa(idMahasiswa) ON DELETE CASCADE
    -- Jika Mahasiswa dihapus, relasi bimbingan ikut terhapus
);

-- Relasi 3-Ary Bimbingan (Cascade di sini)
CREATE TABLE Melakukan (
    idDosen CHAR(6) NOT NULL,
    idMahasiswa CHAR(6) NOT NULL,
    idJadwal INT NOT NULL ,

    PRIMARY KEY (idDosen, idMahasiswa, idJadwal),

    FOREIGN KEY (idDosen) REFERENCES DosenPembimbing(idDosen) ON DELETE CASCADE,
    -- Jika DosenPembimbing dihapus, entri ini ikut terhapus
    FOREIGN KEY (idMahasiswa) REFERENCES Mahasiswa(idMahasiswa) ON DELETE CASCADE,
    -- Jika Mahasiswa dihapus, entri ini ikut terhapus
    FOREIGN KEY (idJadwal) REFERENCES Bimbingan(idJadwal) ON DELETE CASCADE
    -- Jika Bimbingan dihapus, entri ini ikut terhapus
);

-- Relasi Pengirim/Penerima Notifikasi (Cascade di sini)
CREATE TABLE KirimDanTerima (
    idNotifikasi INT NOT NULL,
    idPengirim CHAR(6) NOT NULL,
    idPenerima CHAR(6) NOT NULL,

    PRIMARY KEY (idPengirim, idPenerima , idNotifikasi),

    FOREIGN KEY (idNotifikasi) REFERENCES Notifikasi(idNotifikasi) ON DELETE CASCADE,
    -- Jika Notifikasi dihapus, entri ini ikut terhapus
    FOREIGN KEY (idPengirim) REFERENCES Pengguna(idPengguna) ON DELETE CASCADE,
    -- Jika Pengirim (Pengguna) dihapus, entri ini ikut terhapus
    FOREIGN KEY (idPenerima) REFERENCES Pengguna(idPengguna) ON DELETE CASCADE
    -- Jika Penerima (Pengguna) dihapus, entri ini ikut terhapus
);

---Tabel View---
CREATE OR REPLACE VIEW ViewBimbinganLengkap AS
SELECT
    ROW_NUMBER() OVER (
        PARTITION BY m.idMahasiswa
        ORDER BY b.tanggal
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
    b.tanggal,
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
('D12135','Raymond Chandra Putra','UnparDosen123','raymond.chandra@unpar.ac.id', false),
('D09005','Vania Natali','DosenUnpar321','vania.natali@unpar.ac.id', false),
('M23023','Vandyka Suryadi','KecapABC','6182301023@student.unpar.ac.id', false),
('M23031','Alexander Constantijn','KecapABC','6182301031@student.unpar.ac.id', false),
('M23075','Keane Edbert Candra','KecapABC','6182301075@student.unpar.ac.id', false),
('M23079','Alfonsus Nugraha Adi Prakosa','KecapABC','6182301079@student.unpar.ac.id', false),
('M23084','Justin Farrel Kristianto','KecapABC','6182301084@student.unpar.ac.id', false),
('A23001','Kenneth Nathanael','Adminboy','Kenken@unpar.ac.id', false),
('A23002','Gregorius Jason Maresi','Adminboy','Greg@unpar.ac.id', false),
('A23003','Andrew Kevin Alexander','Adminboy','Endru@.unpar.ac.id', false);

INSERT INTO DosenPembimbing VALUES
('D12135'),
('D09005');

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
('M23023',2, 2, 'RCP6001ACS'),
('M23031',2, 1, 'RCP6002ACS'),
('M23075',2, 1, 'VAN6005BDS'),
('M23079',2, 0, 'VAN6004CDS'),
('M23084',2, 0, 'RCP6003BCS');

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

INSERT INTO Jadwal (hari, jamMulai, jamSelesai, nomorRuangan) VALUES
('Senin','08:00','10:00',9015),--1
('Senin','10:00','12:00',9016),--2
('Senin','13:00','15:00',9015),--3
('Senin','16:00','17:00',9018),--4
('Selasa','08:00','10:00',9015),--5 ------
('Selasa','10:00','12:00',9017),--6
('Selasa','13:00','15:00',9016),--7
('Selasa','16:00','17:00',9017),--8
('Rabu','08:00','10:00',10111),--9
('Rabu','10:00','12:00',9018),--10
('Rabu','13:00','15:00',10111),--11
('Rabu','16:00','17:00',9018),--12
('Kamis','08:00','10:00',9120),--13
('Kamis','10:00','12:00',9121),--14
('Kamis','13:00','15:00',9018),--15
('Kamis','16:00','17:00',9018),--16
('Jumat','08:00','10:00',9121),--17
('Jumat','10:00','12:00',9018),--18
('Jumat','13:00','15:00',9121),--19
('Jumat','16:00','17:00',9018);--20


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
(2,'2025-09-10','Membuat database dengan data dummy','Pembuatan database',1),--senin 10-12
(5,'2025-10-11','Memperbaiki algoritma pencarian','Perbaikan algoritma A*',3),--selase 8-10
(6,'2025-10-11','Memperbaiki fitur-fitur','Perbaikan fitur filter berdasarkan nama',null),--selasa 10-12
(8,'2025-10-11','Menghilangkan redundant dalam kode','Menghapus looping dalam kode',4),--selasa 16-18
(10,'2025-11-12','Membuat algoritma pencarian baru','Membuat algoritma BFS',null),-- rabu 10-12
(12,'2025-11-12','Memperbaiki bug','Membuat algoritma BFS',3),-- rabu 16-18
(13,'2025-12-13','Menerapkan cookies','Membuat algoritma BFS',4),-- kamis 8-10
(15,'2025-12-13','Menghilangkan kode redundant','Membuat algoritma BFS',null),-- kamis 13-15
(16,'2025-12-13',NULL,NULL,NULL),-- kamis 16-18
(18,'2025-09-14',NULL,NULL,5);-- jumat 10-12

INSERT INTO Notifikasi (statusPersetujuan, alasanPenolakan, waktuKirim, tanggalKirim, idJadwal) VALUES
(false,'Belum lengkap', '09:00','2025-09-10',2), --1
(true,NULL,'12:00','2026-10-11',5), --2 -------
(null,NULL,'13:00','2025-10-11',6), --3
(true,NULL,'15:00','2025-10-11',8), --4
(false,'Gagal dijadwalkan','17:00','2025-11-12',10), --5
(true,NULL,'13:00','2025-11-12',12), --6
(false,'Dosen sedang keluar kota','08:00','2025-12-13',13), --7
(false,'Dosen sakit','12:00','2025-12-13',15), --8
(false,'Mahasiswa Sakit','09:00','2025-12-13',16), --9
(True,NULL,'11:00','2025-09-14',18); --10

INSERT INTO KuliahMahaDosen VALUES
('M23031',1),
('M23031',17),
('M23023',3),
('M23075',4),
('M23075',19),
('M23079',11),
('M23084',14),
('M23084',20),
('D12135',7),
('D09005',9);

INSERT INTO Melakukan VALUES
('D12135','M23023',2),
('D12135','M23023',5),
('D12135','M23023',6),
('D09005','M23023',8),
('D09005','M23023',10),
('D12135','M23023',12),
('D12135','M23023',16),
('D09005','M23023',18);


INSERT INTO KirimDanTerima VALUES
(1,'D12135','M23023'),--2
(2,'D12135','M23023'),--5
(3,'D12135','M23023'),--6
(4,'D09005','M23023'),--8
(5,'D09005','M23023'),--10
(6,'D12135','M23023'),--12
(7,'M23031','D12135'),--13 --tolak
(8,'M23075','D09005'),--15 --tolak
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

--SELECT * FROM notifikasi;

INSERT INTO Ruangan (nomorRuangan, namaRuangan, statusRuangan, jenisRuangan)
VALUES
(101, 'Ruang Bimbingan 101', true, false),
(102, 'Ruang Bimbingan 102', true, false),
(201, 'Ruang Riset 201', true, true),
(301, 'Ruang Konsultasi 301', false, false), -- tidak aktif
(401, 'Ruang Dosen 401', true, true);

INSERT INTO Jadwal (hari, jamMulai, jamSelesai, nomorRuangan)
VALUES
('Rabu', '09:00', '11:00', 101),
('Rabu', '13:00', '15:00', 101),
('Rabu', '10:00', '12:00', 102),
('Rabu', '08:00', '10:00', 201),
('Rabu', '14:00', '16:00', 401);