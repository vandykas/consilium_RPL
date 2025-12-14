const API_URL = '/api/admin/mahasiswa';

const modalEdit = document.getElementById('editModal');
const formEdit = document.getElementById('formEditMahasiswa');

window.openEditModal = function (idMahasiswa) {
};

window.closeEditModal = function () {
    modalEdit.style.display = 'none';
};

const tambahMahasiswaModal = document.getElementById('tambahMahasiswaModal');

function showTambahMahasiswaModal() {
    if (tambahMahasiswaModal) {
        tambahMahasiswaModal.style.display = 'block';
    }
}

function closeTambahMahasiswaModal() {
    if (tambahMahasiswaModal) {
        tambahMahasiswaModal.style.display = 'none';
        document.getElementById('formTambahMahasiswa').reset();
    }
}

document.addEventListener('DOMContentLoaded', () => {

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

    if (formEdit) {
        formEdit.addEventListener('submit', function (e) {
        });
    }

    window.onclick = function (event) {
        if (event.target == modalEdit) {
            closeEditModal();
        }
        if (event.target == tambahMahasiswaModal) {
            closeTambahMahasiswaModal();
        }
    }

    const tambahMahasiswaButton = document.getElementById('tambahMahasiswaButton');

    if (tambahMahasiswaButton) {
        tambahMahasiswaButton.addEventListener('click', showTambahMahasiswaModal);
    }
});