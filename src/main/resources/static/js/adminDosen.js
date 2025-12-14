const API_BASE_URL = '/api/admin/dosen'; 
const editModal = document.getElementById('editModalDosen');

// ---- Logika Edit Data Dosen ----

function fillDosenData(data) {
    document.getElementById('editIdDosen').value = data.id;
    document.getElementById('editEmailDosen').value = data.email;
    document.getElementById('editNamaDosen').value = data.nama;
}

function showEditModal() {
    if (editModal) {
        editModal.style.display = 'block';
    }
}

function closeEditModal() {
    if (editModal) {
        editModal.style.display = 'none';
        document.getElementById('formEditDosen').reset();
    }
}

window.onclick = function(event) {
    if (event.target === editModal) {
        closeEditModal();
    }
};

document.addEventListener('DOMContentLoaded', () => {
    const editButtons = document.querySelectorAll('.edit-button-dosen');

    editButtons.forEach(button => {
        button.addEventListener('click', function() {
            const idDosen = this.getAttribute('data-id');
            
            fetch(`${API_BASE_URL}/${idDosen}`)
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`Gagal mengambil data. Status: ${response.status}`);
                    }
                    return response.json();
                })
                .then(data => {
                    fillDosenData(data); 
                    showEditModal();
                })
                .catch(error => {
                    console.error('Error saat mengambil data Dosen:', error);
                    alert(`Gagal memuat data dosen (NIP: ${idDosen}). Periksa console untuk detail.`);
                });
        });
    });

    const form = document.getElementById('formEditDosen');
    if (form) {
        form.addEventListener('submit', function(event) {
            event.preventDefault();
            
            const formData = new FormData(form);
            const dataToUpdate = {};
            formData.forEach((value, key) => dataToUpdate[key] = value);

            const idDosen = dataToUpdate.idDosen;

            fetch(`${API_BASE_URL}/${idDosen}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(dataToUpdate)
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`Gagal memperbarui data. Status: ${response.status}`);
                }
                return response.json();
            })
            .then(result => {
                alert("Data Dosen berhasil diperbarui!");
                closeEditModal();
                window.location.reload(); 
            })
            .catch(error => {
                console.error('Error saat memperbarui data Dosen:', error);
                alert(`Gagal memperbarui data dosen. Detail: ${error.message}`);
            });
        });
    }

    const tambahDosenButton = document.getElementById('tambahDosenButton');
    const formTambahDosen = document.getElementById('formTambahDosen');

    if (tambahDosenButton) {
        tambahDosenButton.addEventListener('click', showTambahDosenModal);
    }
    
    if (formTambahDosen) {
        formTambahDosen.addEventListener('submit', function(event) {
        });
    }

});

// --- LOGIKA MODAL TAMBAH DOSEN ---

const tambahDosenModal = document.getElementById('tambahDosenModal');
const tambahDosenButton = document.getElementById('tambahDosenButton');

function showTambahDosenModal() {
    if (tambahDosenModal) {
        tambahDosenModal.style.display = 'block';
    }
}

function closeTambahDosenModal() {
    if (tambahDosenModal) {
        tambahDosenModal.style.display = 'none';
        document.getElementById('formTambahDosen').reset();
    }
}

if (tambahDosenButton) {
    tambahDosenButton.addEventListener('click', showTambahDosenModal);
}
