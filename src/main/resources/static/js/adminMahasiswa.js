const API_URL = '/api/admin/mahasiswa';

document.addEventListener('DOMContentLoaded', () => {
    const modal = document.getElementById('editModal');
    const form = document.getElementById('formEditMahasiswa');

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
                document.getElementById('editIdMahasiswa').value = data.idMahasiswa;
                document.getElementById('editEmail').value = data.email;
                document.getElementById('editNama').value = data.nama;
                document.getElementById('editTopik').value = data.kodeTopik;
                document.getElementById('editNamaDosenDisplay').innerText = data.namaDosen || 'N/A';
            })
            .catch(error => {
                alert("Gagal mengambil data mahasiswa: " + error.message);
                console.error('Error:', error);
                closeEditModal();
            });
    }

    window.closeEditModal = function () {
        modal.style.display = 'none';
    }

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

    document.querySelectorAll('.edit-button').forEach(button => {
        const idMahasiswa = button.getAttribute('data-id');

        button.addEventListener('click', () => {
            if (idMahasiswa) {
                openEditModal(idMahasiswa);
            } else {
                console.error("ID Mahasiswa tidak ditemukan di tombol.");
            }
        });
    });

    window.onclick = function (event) {
        if (event.target == modal) {
            closeEditModal();
        }
    }
});