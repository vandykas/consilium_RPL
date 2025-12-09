(() => {
    const buttPengajuan = document.querySelector('#button-container > button:nth-child(2)');
    buttPengajuan.addEventListener('click', openPengajuanForm);

    const closeButton = document.querySelector('.button-group > button:nth-child(2)');
    closeButton.addEventListener('click', closePengajuanForm);

    // Tambahan: Ambil elemen HTML
    const tanggalInput = document.getElementById("tanggal-bimbingan");
    const jamMulaiSelect = document.getElementById("jam-mulai");
    const jamSelesaiSelect = document.getElementById("jam-selesai");
    const ruanganSelect = document.getElementById("ruangan");

    // Event — ketika tanggal dipilih
    tanggalInput.addEventListener("change", async () => {
        const tanggal = tanggalInput.value;

        if (!tanggal) return;

        // Clear dropdown lain
        jamMulaiSelect.innerHTML = `<option value="">Pilih jam mulai</option>`;
        jamSelesaiSelect.innerHTML = `<option value="">Pilih jam selesai</option>`;
        ruanganSelect.innerHTML = `<option value="">Pilih ruangan</option>`;

        const response = await fetch(`/api/jadwal/available-start?tanggal=${tanggal}`);
        const data = await response.json();

        data.forEach(time => {
            const opt = document.createElement("option");
            opt.value = time;
            opt.textContent = time.substring(0, 5);
            jamMulaiSelect.appendChild(opt);
        });
    });

    // Event — ketika jam mulai dipilih
    jamMulaiSelect.addEventListener("change", async () => {
        const tanggal = tanggalInput.value;
        const mulai = jamMulaiSelect.value;

        if (!tanggal || !mulai) return;

        jamSelesaiSelect.innerHTML = `<option value="">Pilih jam selesai</option>`;
        ruanganSelect.innerHTML = `<option value="">Pilih ruangan</option>`;

        const response = await fetch(`/api/jadwal/available-end?tanggal=${tanggal}&mulai=${mulai}`);
        const data = await response.json();

        data.forEach(time => {
            const opt = document.createElement("option");
            opt.value = time;
            opt.textContent = time.substring(0, 5);
            jamSelesaiSelect.appendChild(opt);
        });
    });

    // Event — ketika jam selesai dipilih
    jamSelesaiSelect.addEventListener("change", async () => {
        const tanggal = tanggalInput.value;
        const mulai = jamMulaiSelect.value;
        const selesai = jamSelesaiSelect.value;

        if (!tanggal || !mulai || !selesai) return;

        ruanganSelect.innerHTML = `<option value="">Pilih ruangan</option>`;

        const response = await fetch(`/api/jadwal/available-ruangan?tanggal=${tanggal}&mulai=${mulai}&selesai=${selesai}`);
        const data = await response.json();

        data.forEach(room => {
            const opt = document.createElement("option");
            opt.value = room;
            opt.textContent = room;
            ruanganSelect.appendChild(opt);
        });
    });

})();

function openPengajuanForm() {
    const form = document.querySelector('#overlay-pengajuan');
    form.classList.remove('hidden');
}

function closePengajuanForm() {
    const form = document.querySelector('#overlay-pengajuan');
    form.classList.add('hidden');
}
