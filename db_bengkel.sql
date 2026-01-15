-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 14 Jan 2026 pada 17.09
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_bengkel`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `jasa_service`
--

CREATE TABLE `jasa_service` (
  `id_jasa` int(11) NOT NULL,
  `nama_jasa` varchar(100) NOT NULL,
  `harga_standar` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `jasa_service`
--

INSERT INTO `jasa_service` (`id_jasa`, `nama_jasa`, `harga_standar`) VALUES
(2, 'Full Service ', 120000),
(3, 'Ganti Oli', 25000),
(4, 'Ganti Kampas Rem', 30000),
(5, 'Ganti Oli Dan Kampas Rem ', 50000),
(6, 'Ganti Oli Dan Filter ', 35000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `kategori_part`
--

CREATE TABLE `kategori_part` (
  `id_kategori` int(11) NOT NULL,
  `nama_kategori` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `kendaraan`
--

CREATE TABLE `kendaraan` (
  `no_polisi` varchar(15) NOT NULL,
  `merk` varchar(50) DEFAULT NULL,
  `model` varchar(50) DEFAULT NULL,
  `warna` varchar(20) DEFAULT NULL,
  `tahun` int(11) DEFAULT NULL,
  `id_pelanggan` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `kendaraan`
--

INSERT INTO `kendaraan` (`no_polisi`, `merk`, `model`, `warna`, `tahun`, `id_pelanggan`) VALUES
('BM 2261 SZ', 'DAIHATSU', 'SUV/TERUIS', 'HITAM', 2024, 11),
('BM 3221 PK', 'TOYOTA', 'SEDAN/VIOS', 'PUTIH', 2011, 9),
('BM 4639 YQ', 'TOYOTA', 'LMPV/KIJANG', 'BIRU', 1999, 12),
('BM 551 OK', 'DAIHATSU', 'SUV/TERIOS', 'PUTIH', 2023, 10),
('BM 6831 SAQ', 'TOYOTA', 'JEEP/LANDCRUISER', 'HITAM', 1997, 8),
('BZ 2250 AK', 'GTR', 'FORTUNER', 'JINGGA', 2011, 13);

-- --------------------------------------------------------

--
-- Struktur dari tabel `mekanik`
--

CREATE TABLE `mekanik` (
  `id_mekanik` int(11) NOT NULL,
  `nama_mekanik` varchar(100) NOT NULL,
  `spesialisasi` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `mekanik`
--

INSERT INTO `mekanik` (`id_mekanik`, `nama_mekanik`, `spesialisasi`) VALUES
(2, 'SAMUEL ', 'MESIN'),
(3, 'MANIK', 'KELISTRIKAN'),
(4, 'BOYKE', 'TRANSMISI'),
(5, 'SANDRO ', 'MESIN'),
(6, 'PUKIAKU', 'KELISTRIKAN'),
(7, 'SUKIAKU', 'TRANSMISI');

-- --------------------------------------------------------

--
-- Struktur dari tabel `pelanggan`
--

CREATE TABLE `pelanggan` (
  `id_pelanggan` int(11) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `no_hp` varchar(15) DEFAULT NULL,
  `alamat` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `pelanggan`
--

INSERT INTO `pelanggan` (`id_pelanggan`, `nama`, `no_hp`, `alamat`) VALUES
(8, 'Yohanes Letare', '081275725864', 'Tegal sari'),
(9, 'Margogo', '082258888111', 'tegal oke'),
(10, 'rendi', '081122335544', 'tegal putus'),
(11, 'MARGOGO ARYANO', '082286131676', 'JL TEGAL SARI NO 88A'),
(12, 'Yohanes Letare', '081275725864', 'Jl Tegal Sari no 88a'),
(13, 'EBEZEK', '0895603177863', 'JALANKU');

-- --------------------------------------------------------

--
-- Struktur dari tabel `service_detail_jasa`
--

CREATE TABLE `service_detail_jasa` (
  `id_detail_jasa` int(11) NOT NULL,
  `id_service` int(11) DEFAULT NULL,
  `id_jasa` int(11) DEFAULT NULL,
  `harga_deal` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `service_detail_jasa`
--

INSERT INTO `service_detail_jasa` (`id_detail_jasa`, `id_service`, `id_jasa`, `harga_deal`) VALUES
(9, 19, 2, 120000),
(10, 20, 3, 25000),
(11, 21, 6, 35000),
(12, 22, 4, 30000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `service_detail_part`
--

CREATE TABLE `service_detail_part` (
  `id_detail_part` int(11) NOT NULL,
  `id_service` int(11) DEFAULT NULL,
  `kode_part` varchar(20) DEFAULT NULL,
  `qty` int(11) NOT NULL,
  `subtotal_part` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `service_detail_part`
--

INSERT INTO `service_detail_part` (`id_detail_part`, `id_service`, `kode_part`, `qty`, `subtotal_part`) VALUES
(26, 19, '002', 1, 200000),
(27, 19, '001', 1, 55000),
(28, 19, '110', 1, 55000),
(29, 19, '211', 1, 90000),
(30, 20, '004', 1, 280000),
(31, 21, 'LUB-004', 1, 210000),
(32, 21, 'FIL-003', 1, 95000),
(33, 22, 'WIP-005', 1, 70);

-- --------------------------------------------------------

--
-- Struktur dari tabel `service_header`
--

CREATE TABLE `service_header` (
  `id_service` int(11) NOT NULL,
  `tgl_service` date NOT NULL,
  `no_polisi` varchar(15) DEFAULT NULL,
  `id_mekanik` int(11) DEFAULT NULL,
  `keluhan_konsumen` text DEFAULT NULL,
  `total_biaya` double DEFAULT 0,
  `status` enum('Antri','Proses','Selesai','Batal') DEFAULT 'Antri'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `service_header`
--

INSERT INTO `service_header` (`id_service`, `tgl_service`, `no_polisi`, `id_mekanik`, `keluhan_konsumen`, `total_biaya`, `status`) VALUES
(19, '2026-01-13', 'BM 4639 YQ', 2, 'Rem Bunyi dan mobil berasap', 520000, 'Selesai'),
(20, '2026-01-13', 'BM 4639 YQ', 2, 'gitulah', 305000, 'Selesai'),
(21, '2026-01-14', 'BM 4639 YQ', 2, 'SUARA KASAR', 340000, 'Selesai'),
(22, '2026-01-14', 'BZ 2250 AK', 6, 'DAH MAU MATOT', 30070, 'Selesai');

-- --------------------------------------------------------

--
-- Struktur dari tabel `sparepart`
--

CREATE TABLE `sparepart` (
  `kode_part` varchar(20) NOT NULL,
  `nama_part` varchar(100) NOT NULL,
  `harga_beli` double NOT NULL,
  `harga_jual` double NOT NULL,
  `stok` int(11) DEFAULT 0,
  `id_kategori` int(11) DEFAULT NULL,
  `id_supplier` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `sparepart`
--

INSERT INTO `sparepart` (`kode_part`, `nama_part`, `harga_beli`, `harga_jual`, `stok`, `id_kategori`, `id_supplier`) VALUES
('001', 'OLI UNIOIL 1L', 40000, 55000, 99, NULL, NULL),
('002', 'OLI UNIOIL 4L', 150000, 200000, 99, NULL, NULL),
('003', 'OLI SHELL HELIX 1L', 55000, 70000, 80, NULL, NULL),
('004', 'OLI SHELL HELIX 4L', 220000, 280000, 79, NULL, NULL),
('110', 'FILTER OLI SAKURA FOR KIJANG', 35000, 55000, 99, NULL, NULL),
('111', 'FILTER OLI SAKURA FOR DAIHATSU', 30000, 45000, 100, NULL, NULL),
('112', 'FILTER OLI SAKURA FOR TOYOTA', 31500, 45000, 100, NULL, NULL),
('211', 'KAMPAS REM FOR TOYOTA', 70000, 90000, 99, NULL, NULL),
('222', 'KAMPAS REM FOR DAIHATSU', 68000, 89000, 100, NULL, NULL),
('ACC-001', 'PARFUM MOBIL CALIFORNIA SCENTS', 28, 40, 854, NULL, NULL),
('ACC-002', 'KANEBO/LAP CHAMOIS (AION PLAS CHAMOIS)', 45, 65, 88, NULL, NULL),
('ACC-003', 'KUNCI RODA PALANG (CROSS WRENCH)', 60, 90, 676, NULL, NULL),
('ACC-004', 'DONGKRAK BUAYA 2 TON (TEKIRO)', 350, 475, 36, NULL, NULL),
('BLT-001', 'FAN BELT AVANZA 1.3 (ORIGINAL) 4PK', 80, 110, 138, NULL, NULL),
('BLT-002', 'FAN BELT INNOVA BENSIN (ORIGINAL) 7PK', 300, 385, 93, NULL, NULL),
('BRK-001', 'Kampas Rem Depan Avanza/Xenia (Akebono)', 135000, 175000, 100, NULL, NULL),
('BRK-002', 'Kampas Rem Depan Innova Lama (Birken)', 150000, 200000, 95, NULL, NULL),
('BRK-003', 'Kampas Rem Depan Brio/Mobilio (Nissin)', 350000, 450000, 90, NULL, NULL),
('BRK-004', 'Kampas Rem Depan Pajero Sport (Original)', 850000, 1100000, 100, NULL, NULL),
('BRK-005', 'Kampas Rem Belakang (Tromol) Avanza', 110000, 160000, 88, NULL, NULL),
('CHM-001', 'RADIATOR COOLANT PRESTONE (GALON 4L)', 85, 120, 20, NULL, NULL),
('CHM-002', 'RADIATOR COOLANT MEGACOOLS (GALON 5L)', 40, 65, 73, NULL, NULL),
('CHM-003', 'CAIRAN WIPER (WIPER FLUID) KIT', 10, 20, 12, NULL, NULL),
('CHM-004', 'ENGINE FLUSH STP (KALENG)', 55, 75, 11, NULL, NULL),
('CHM-005', 'CARB/INJECTOR CLEANER (SEMPROTAN)', 30, 50, 19, NULL, NULL),
('ELC-001', 'Busi Denso XU22PR9 (Avanza/Xenia)/pc', 22000, 30000, 90, NULL, NULL),
('ELC-002', 'Busi NGK LZKAR6C (Livina/Marc) /pc', 28000, 40000, 98, NULL, NULL),
('ELC-003', 'Busi Iridium Denso (Universal) /pc', 90000, 120000, 91, NULL, NULL),
('ELC-004', 'Bohlam Depan H4 Osram 12V 60/55W', 25000, 40000, 100, NULL, NULL),
('ELC-005', 'Bohlam Depan H4 Philips X-trem Vision', 180000, 250000, 94, NULL, NULL),
('ELC-006', 'Bohlam Rem Belakang (Kaki 2) /dus isi 10', 30000, 50000, 99, NULL, NULL),
('ELC-007', 'Klakson Denso Keong Waterproof (Set)', 115000, 165000, 96, NULL, NULL),
('ELC-008', 'Klakson Hella Disc Compact (Set)', 90000, 135000, 100, NULL, NULL),
('FIL-001', 'Filter Oli Avanza/Xenia/Rush (Aspira)', 24000, 35000, 91, NULL, NULL),
('FIL-002', 'Filter Oli Avanza/Xenia/Rush (Denso)', 28000, 40000, 94, NULL, NULL),
('FIL-003', 'Filter Oli Innova/Fortuner Bensin(Original)', 75000, 95000, 98, NULL, NULL),
('FIL-004', 'Filter Oli Innova Reborn Diesel (Original)', 85000, 110000, 92, NULL, NULL),
('FIL-005', 'Filter Oli Honda Jazz/Brio/Mobilio (Denso)', 30000, 45000, 87, NULL, NULL),
('FIL-006', 'Filter Oli Grand Livina (Original)', 35000, 50000, 90, NULL, NULL),
('FIL-007', 'Filter Udara Avanza/Xenis VVT-i (Denso)', 60000, 80000, 68, NULL, NULL),
('FIL-008', 'Filter Udara All New Avnza/Xenia 1.3', 65000, 85000, 97, NULL, NULL),
('FIL-009', 'Filter Udara Inova Reborn (Sakura)', 85000, 115000, 97, NULL, NULL),
('FIL-010', 'Filter Udara Honda Brio/Mobilio (Sakra', 65000, 90000, 88, NULL, NULL),
('FIL-011', 'Filter AC (Cabin) Avanza/Xenia (Polos)', 30000, 55000, 89, NULL, NULL),
('FIL-012', 'Filter AC (Cabin) Honda Brio/Jazz (Carbon)', 45000, 75000, 79, NULL, NULL),
('LUB-001', 'Oli Shell Helix HX5 15W-40(4 Liter)', 285000, 360000, 77, NULL, NULL),
('LUB-002', 'Oli hell Helix HX6 10W-40 (4 Liter)', 315000, 395000, 88, NULL, NULL),
('LUB-003', 'Oli Pertamina Fastron Techno 10W-40 (4liter)', 310000, 385000, 69, NULL, NULL),
('LUB-004', 'Oli Pertamina Prima X4 20W-50 (4 Liter)', 165000, 210000, 70, NULL, NULL),
('LUB-005', 'Oli Castrol Magnatec 10W-40 (4 Liter)', 340000, 425000, 57, NULL, NULL),
('LUB-006', 'Oli Transmisi ATF-IV Toyota (Galon 4liter)', 350000, 450000, 70, NULL, NULL),
('LUB-007', 'Oli Transmisi CVT FE Toyota (Galon 4 Liter)', 390000, 495000, 87, NULL, NULL),
('LUB-008', 'Oli Gardan Rored HDA 90 (1 Liter)', 45000, 65000, 59, NULL, NULL),
('LUB-009', 'Minyak Rem Prestone DOT 3 (300ml)', 25000, 35000, 76, NULL, NULL),
('LUB-010', 'Minyak Rem Jumbo DOT 3 (Merah/Netral)', 15000, 25000, 90, NULL, NULL),
('SUS-001', 'SHOCKBREAKER BELAKANG AVANZA (KYB)/SET', 550, 725, 19, NULL, NULL),
('SUS-002', 'LINK STABILIZER AVANZA/XENIA (555 JEPANG)', 120, 165, 15, NULL, NULL),
('SUS-003', 'KARET BOOTH AS RODA (UNIVERSAL)', 35, 60, 18, NULL, NULL),
('TRM-001', 'KAMPAS KOPLING (DISC) AVANZA 1.3(ORIGINAL)', 450, 585, 46, NULL, NULL),
('TRM-002', 'DEKRUP (CLUTCH COVER) AVANZA 1.3 (ORIGINAL)', 410, 535, 47, NULL, NULL),
('TRM-003', 'DRUG LAGER (RELEASE BEARING) AVANZA (NSK)', 115, 165, 54, NULL, NULL),
('TRM-004', 'PAKET KOPLING SET AVANZA 1.3 (EXEDY) - 3 ITEM', 950000, 1250000, 25, NULL, NULL),
('TRM-005', 'KAMPAS KOPLING INNOVA BENSIN (ORIGINAL TOYOTA)', 860000, 1100000, 93, NULL, NULL),
('TRM-006', 'DEKRUP INNOVA BENSIN (ORIGINAL TOYOTA)', 760000, 980000, 478, NULL, NULL),
('TRM-007', 'KAMPAS KOPLING GRAND MAX / RUSH (DAIHATSU)', 510000, 670000, 38, NULL, NULL),
('TRM-008', 'KAMPAS KOPLING L300 DIESEL (EXEDY JEPANG)', 420000, 560000, 88, NULL, NULL),
('TRM-009', 'MASTER KOPLING ATAS(CM ASSY) AVANZA (AISIN)', 225000, 310000, 77, NULL, NULL),
('TRM-010', 'MASTER KOPLING BAWAH (CO ASSY) AVANZA (AISIN)', 135000, 190000, 83, NULL, NULL),
('TRM-011', 'KABEL KOPLING AVANZA/XENIA LAMA (ORIGINAL)', 125000, 180000, 37, NULL, NULL),
('TRM-012', 'SEAL KRUK AS BELAKANG (REAR MAIN SEAL) AVANZA', 90000, 135000, 88, NULL, NULL),
('TRM-013', 'SEAL DRIVE SHAFT / AS RODA DEPAN (KANAN/KIRI)', 45000, 75000, 36, NULL, NULL),
('TRM-014', 'DILTER OLI TRANSMISI MATIC (STRAINER) AVANZA/RUSH', 190000, 265000, 48, NULL, NULL),
('TRM-015', 'PACKING/GASKET KARTER MATIC (RUBBER/GABUS)', 65000, 100000, 999, NULL, NULL),
('WIP-001', 'WIPER BOSCH ADVANTAGE 14 INCH', 35, 50, 32, NULL, NULL),
('WIP-002', 'WIPER BOSCH ADVANTAGE 16 INCH', 35, 50, 45, NULL, NULL),
('WIP-003', 'WIPER BOSCH ADVANTAGE 18 INCH', 40, 55, 23, NULL, NULL),
('WIP-004', 'WIPER BOSCH ADVANTAGE 20 INCH', 45, 60, 88, NULL, NULL),
('WIP-005', 'WIPER BOSCH ADVANTAGE 22 INCH', 50, 70, 76, NULL, NULL),
('WIP-006', 'WIPER BELAKANG (REAR) AVANZA 14\"', 30, 50, 99, NULL, NULL);

-- --------------------------------------------------------

--
-- Struktur dari tabel `supplier`
--

CREATE TABLE `supplier` (
  `id_supplier` int(11) NOT NULL,
  `nama_supplier` varchar(100) NOT NULL,
  `no_telp` varchar(15) DEFAULT NULL,
  `alamat_kantor` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `supplier`
--

INSERT INTO `supplier` (`id_supplier`, `nama_supplier`, `no_telp`, `alamat_kantor`) VALUES
(1, 'PT. UNIOIL INDONESIA', '0761123', 'JL MERDEKA'),
(2, 'PT. SHELL INDONESIA', '0751321', 'JL INDONESIA'),
(3, 'PT. UNIVERSAL FILTER OEM', '055123', 'JL DURIAN'),
(4, 'PT. TOYOTA INDONESIA', '0897555', 'JL KASUWARI'),
(5, 'PT. DAIHATSU', '0654222', 'JL INDRAMAJU');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `jasa_service`
--
ALTER TABLE `jasa_service`
  ADD PRIMARY KEY (`id_jasa`);

--
-- Indeks untuk tabel `kategori_part`
--
ALTER TABLE `kategori_part`
  ADD PRIMARY KEY (`id_kategori`);

--
-- Indeks untuk tabel `kendaraan`
--
ALTER TABLE `kendaraan`
  ADD PRIMARY KEY (`no_polisi`),
  ADD KEY `fk_pelanggan_auto_hapus` (`id_pelanggan`);

--
-- Indeks untuk tabel `mekanik`
--
ALTER TABLE `mekanik`
  ADD PRIMARY KEY (`id_mekanik`);

--
-- Indeks untuk tabel `pelanggan`
--
ALTER TABLE `pelanggan`
  ADD PRIMARY KEY (`id_pelanggan`);

--
-- Indeks untuk tabel `service_detail_jasa`
--
ALTER TABLE `service_detail_jasa`
  ADD PRIMARY KEY (`id_detail_jasa`),
  ADD KEY `id_service` (`id_service`),
  ADD KEY `id_jasa` (`id_jasa`);

--
-- Indeks untuk tabel `service_detail_part`
--
ALTER TABLE `service_detail_part`
  ADD PRIMARY KEY (`id_detail_part`),
  ADD KEY `id_service` (`id_service`),
  ADD KEY `kode_part` (`kode_part`);

--
-- Indeks untuk tabel `service_header`
--
ALTER TABLE `service_header`
  ADD PRIMARY KEY (`id_service`),
  ADD KEY `id_mekanik` (`id_mekanik`),
  ADD KEY `fk_kendaraan_auto_hapus` (`no_polisi`);

--
-- Indeks untuk tabel `sparepart`
--
ALTER TABLE `sparepart`
  ADD PRIMARY KEY (`kode_part`),
  ADD KEY `id_kategori` (`id_kategori`),
  ADD KEY `id_supplier` (`id_supplier`);

--
-- Indeks untuk tabel `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`id_supplier`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `jasa_service`
--
ALTER TABLE `jasa_service`
  MODIFY `id_jasa` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT untuk tabel `kategori_part`
--
ALTER TABLE `kategori_part`
  MODIFY `id_kategori` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT untuk tabel `mekanik`
--
ALTER TABLE `mekanik`
  MODIFY `id_mekanik` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT untuk tabel `pelanggan`
--
ALTER TABLE `pelanggan`
  MODIFY `id_pelanggan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT untuk tabel `service_detail_jasa`
--
ALTER TABLE `service_detail_jasa`
  MODIFY `id_detail_jasa` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT untuk tabel `service_detail_part`
--
ALTER TABLE `service_detail_part`
  MODIFY `id_detail_part` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT untuk tabel `service_header`
--
ALTER TABLE `service_header`
  MODIFY `id_service` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT untuk tabel `supplier`
--
ALTER TABLE `supplier`
  MODIFY `id_supplier` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `kendaraan`
--
ALTER TABLE `kendaraan`
  ADD CONSTRAINT `fk_pelanggan_auto_hapus` FOREIGN KEY (`id_pelanggan`) REFERENCES `pelanggan` (`id_pelanggan`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `service_detail_jasa`
--
ALTER TABLE `service_detail_jasa`
  ADD CONSTRAINT `service_detail_jasa_ibfk_1` FOREIGN KEY (`id_service`) REFERENCES `service_header` (`id_service`) ON DELETE CASCADE,
  ADD CONSTRAINT `service_detail_jasa_ibfk_2` FOREIGN KEY (`id_jasa`) REFERENCES `jasa_service` (`id_jasa`);

--
-- Ketidakleluasaan untuk tabel `service_detail_part`
--
ALTER TABLE `service_detail_part`
  ADD CONSTRAINT `service_detail_part_ibfk_1` FOREIGN KEY (`id_service`) REFERENCES `service_header` (`id_service`) ON DELETE CASCADE,
  ADD CONSTRAINT `service_detail_part_ibfk_2` FOREIGN KEY (`kode_part`) REFERENCES `sparepart` (`kode_part`);

--
-- Ketidakleluasaan untuk tabel `service_header`
--
ALTER TABLE `service_header`
  ADD CONSTRAINT `fk_kendaraan_auto_hapus` FOREIGN KEY (`no_polisi`) REFERENCES `kendaraan` (`no_polisi`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `service_header_ibfk_1` FOREIGN KEY (`no_polisi`) REFERENCES `kendaraan` (`no_polisi`),
  ADD CONSTRAINT `service_header_ibfk_2` FOREIGN KEY (`id_mekanik`) REFERENCES `mekanik` (`id_mekanik`);

--
-- Ketidakleluasaan untuk tabel `sparepart`
--
ALTER TABLE `sparepart`
  ADD CONSTRAINT `sparepart_ibfk_1` FOREIGN KEY (`id_kategori`) REFERENCES `kategori_part` (`id_kategori`),
  ADD CONSTRAINT `sparepart_ibfk_2` FOREIGN KEY (`id_supplier`) REFERENCES `supplier` (`id_supplier`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
