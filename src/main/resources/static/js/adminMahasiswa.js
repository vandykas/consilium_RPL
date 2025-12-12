// adminMahasiswa.js
// Base URL untuk Rest Controller (Harap diperhatikan: karena ini JS eksternal, URL harus hardcoded atau diambil dari atribut HTML)
const API_URL = '/api/admin/mahasiswa';

document.addEventListener('DOMContentLoaded', () => {
    const modal = document.getElementById('editModal');
    const form = document.getElementById('formEditMahasiswa');

    // --- 1. Fungsi Buka Modal & Fetch Data ---
    window.openEditModal = function (idMahasiswa) {
        modal.style.display = 'block';

        fetch(`${API_URL}/${idMahasiswa}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to fetch data');
                }
                return response.json();
            })
            .then(data => {
                // Isi form dengan data DTO dari Backend
                document.getElementById('editIdMahasiswa').value = data.idMahasiswa;
                document.getElementById('editEmail').value = data.email;
                document.getElementById('editNama').value = data.nama;

                // Set Topik yang sedang dipilih
                document.getElementById('editTopik').value = data.kodeTopik;

                // Tampilkan Dosen Pembimbing (read-only)
                document.getElementById('editNamaDosenDisplay').innerText = data.namaDosen || 'N/A';
            })
            .catch(error => {
                alert("Gagal mengambil data mahasiswa: " + error.message);
                console.error('Error:', error);
                closeEditModal();
            });
    }

    // --- 2. Fungsi Tutup Modal ---
    window.closeEditModal = function () {
        modal.style.display = 'none';
    }

    // --- 3. SUBMIT FORM (UPDATE DATA) ---
    form.addEventListener('submit', function (e) {
        e.preventDefault();

        const formData = {
            idMahasiswa: document.getElementById('editIdMahasiswa').value,
            email: document.getElementById('editEmail').value,
            nama: document.getElementById('editNama').value,
            kodeTopik: document.getElementById('editTopik').value
        };

        fetch(`${API_URL}/update`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        })
            .then(response => {
                if (response.ok) {
                    alert("Data Mahasiswa berhasil diperbarui!");
                    closeEditModal();
                    location.reload();
                } else {
                    return response.text().then(text => Promise.reject(text));
                }
            })
            .catch(error => {
                alert("Gagal memperbarui data. Cek console untuk detail.");
                console.error('Error update:', error);
            });
    });

    // --- 4. ATTACH HANDLER KE TOMBOL EDIT DI TABEL ---
    document.querySelectorAll('.edit-button').forEach(button => {

        // Ambil ID Mahasiswa langsung dari atribut data-id
        const idMahasiswa = button.getAttribute('data-id');

        button.addEventListener('click', () => {
            // Pastikan ID berhasil didapat sebelum memanggil modal
            if (idMahasiswa) {
                openEditModal(idMahasiswa);
            } else {
                console.error("ID Mahasiswa tidak ditemukan di tombol.");
            }
        });
    });

    // --- 5. Tutup modal jika user klik di luar kotak modal ---
    window.onclick = function (event) {
        if (event.target == modal) {
            closeEditModal();
        }
    }
});