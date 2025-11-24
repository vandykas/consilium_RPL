--basic database
CREATE TABLE Pengguna (
	idPengguna char(6) primary key not null,
	nama varchar (60) not null,
	password varchar (20) not  null,
	email varchar (60) not null
);
	
CREATE TABLE DosenPembimbing (
	idDosen char(6) primary key not null,
	foreign key (idDosen) references Pengguna (idPengguna)
);

CREATE TABLE Admin (
	idAdmin char(6) primary key not null,
	foreign key (idAdmin) references Pengguna (idPengguna)
);

CREATE TABLE Mahasiswa (
	idMahasiswa char(6) primary key not null,
	foreign key (idMahasiswa) references Pengguna (idPengguna),
	sebelumUTS int not null,
	setelahUTS int not null
);

CREATE TABLE Jadwal (
    idJadwal SERIAL PRIMARY KEY,
    hari VARCHAR(6) NOT NULL,
    tanggal DATE NOT NULL, --(yyyy-mm-dd)
    jamMulai TIME NOT NULL,--(hh:mm:ss)
    jamSelesai TIME NOT NULL
);

CREATE TABLE Kuliah (
    idJadwal INT PRIMARY KEY,
    FOREIGN KEY (idJadwal) REFERENCES Jadwal(idJadwal)
        ON DELETE CASCADE
);

CREATE TABLE Bimbingan (
    idJadwal INT PRIMARY KEY,
    tugas VARCHAR(250) NOT NULL,
    inti VARCHAR(250) NOT NULL,
    kelompokPerulangan VARCHAR(50),

    FOREIGN KEY (idJadwal) REFERENCES Jadwal(idJadwal)
        ON DELETE CASCADE
);

CREATE TABLE Ruangan (
	nomorRuangan int primary key not null,
	namaRuangan varchar(50) not null,
	statusRuangan varchar(50) not null,
	jenisRuangan boolean not null
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

CREATE TABLE Topik (
	kodeTopik varchar(10) primary key not null,
	judulTopik varchar (200) not null
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
	
)
--1:m

--menambahkan foreign key kode topik untuk mahasiswa
ALTER TABLE Mahasiswa
ADD COLUMN kodeTopik varchar(10) not null,
ADD FOREIGN KEY (kodeTopik) REFERENCES Topik(kodeTopik);

--menambahkan nomor ruangan ke jadwal 
ALTER TABLE Jadwal 
ADD COLUMN nomorRuangan int not null,
ADD FOREIGN KEY (nomorRuangan) REFERENCES Ruangan(nomorRuangan);

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
('M23023', 2, 2, 'RCP6001ACS'),
('M23031', 2, 1, 'RCP6002ACS'),
('M23075', 2, 1, 'VAN6005BDS'),
('M23079', 2, 0, 'VAN6004CDS'),
('M23084', 2, 0, 'RCP6003BCS');

INSERT INTO Ruangan VALUES
(9001,'Ruang Skripsi','Terpakai',false),
(9013,'Lab OwnGames','Kosong',false),
(9014,'Lab Fisika','Kosong',false),
(9015,'Lab 4','Kosong',true),
(9016,'Lab 3','Kosong',true),
(9017,'Lab 2','Terpakai',true),
(9018,'Lab 1','Kosong',false),
(9120,'Kelas 9120','Terpakai',false),
(9121,'Kelas 9121','Kosong',false),
(10111,'Kelas 10111','Kosong',false);

INSERT INTO Jadwal (hari, tanggal, jamMulai, jamSelesai, nomorRuangan) VALUES
('Senin','2025-01-10','08:00','10:00',9015),--1
('Senin','2025-01-10','10:00','12:00',9016),--2
('Selasa','2025-01-11','08:00','10:00',9015),--3
('Selasa','2025-01-11','10:00','12:00',9017),--4
('Rabu','2025-01-12','08:00','10:00',10111),--5
('Rabu','2025-01-12','10:00','12:00',9018),--6
('Kamis','2025-01-13','08:00','10:00',9120),--7
('Kamis','2025-01-13','10:00','12:00',9121),--8
('Jumat','2025-01-14','08:00','10:00',9121),--9
('Jumat','2025-01-14','10:00','12:00',9018);--10

INSERT INTO Kuliah VALUES
(1),(3),(4),(7),(9);

INSERT INTO Bimbingan VALUES
(2,'Membuat database dengan data dummy','Pembuatan database','Senin'),--senin
(5,'Memperbaiki algoritma pencarian','Perbaikan algoritma A*','Rabu'),--rabu
(6,'Memperbaiki fitur-fitur','Perbaikan fitur filter berdasarkan nama',null),--rabu
(8,'Menghilangkan redundant dalam kode','Menghapus looping dalam kode','Kamis'),--kamis
(10,'Membuat algoritma pencarian baru','Membuat algoritma BFS',null);--jumat

INSERT INTO Notifikasi (statusPersetujuan, alasanPenolakan, waktuKirim, tanggalKirim, idJadwal) VALUES
(false,'Belum lengkap', '09:00','2025-01-10',2),
(true,NULL,'12:00','2025-01-12',5),
(true,NULL,'13:00','2025-01-12',6),
(true,NULL,'15:00','2025-01-13',8),
(false,'Gagal dijadwalkan','17:00','2025-01-14',10);

INSERT INTO KuliahMaha VALUES
('M23031',1),
('M23023',3),
('M23075',4);

INSERT INTO KuliahDosen VALUES
('D12135',7),
('D09005',9);

INSERT INTO Melakukan VALUES
('D12135','M23023',2),
('D12135','M23031',5),
('D12135','M23075',6),
('D09005','M23079',8),
('D09005','M23084',10);

INSERT INTO KirimDanTerima VALUES
(1,'M23023','D12135'),--2
(2,'D12135','M23031'),--5
(3,'D12135','M23075'),--6
(4,'D09005','M23079'),--8
(5,'M23084','D09005');--10

insert into DosToStud values
('D12135','M23023'),
('D12135','M23031'),
('D12135','M23084'),
('D09005','M23075'),
('D09005','M23079');





