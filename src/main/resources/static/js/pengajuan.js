(() => {
    initJamMulaiValidOption();
    initJamSelesaiValidOption();
    initRuanganTersediaOption();
})();

let jamValidList = []; // <-- simpan di sini

function initJamMulaiValidOption() {
    const datePicked = document.getElementById('tanggal-bimbingan');
    const jamValidSelect = document.getElementById('jam-mulai');
    const dosenCheckboxes = document.querySelectorAll('input[name="penerima"]');

    const updateJamValid = () => {
        const date = datePicked.value;
        const selectedDosenIds = Array.from(dosenCheckboxes)
            .filter(cb => cb.checked)
            .map(cb => cb.value);

        jamValidSelect.innerHTML = '<option selected disabled>Pilih jam mulai</option>';

        if (!date || selectedDosenIds.length === 0) return;

        const params = new URLSearchParams();
        params.append('tanggal', date);
        selectedDosenIds.forEach(id => params.append('penerima', id));
        console.log(params);
        fetch(`/ambil-jam-valid?${params.toString()}`)
            .then(response => response.json())
            .then(data => {
                jamValidList = data;
                data.map(j => j.substring(0, 5)).forEach(jam => {
                    const option = document.createElement("option");
                    option.value = jam;
                    option.text = jam;
                    jamValidSelect.appendChild(option);
                });
            })
            .catch(error => console.log("Error Fetch:", error));
    };

    datePicked.addEventListener('input', updateJamValid);

    dosenCheckboxes.forEach(cb => {
        cb.addEventListener('change', updateJamValid);
    });
}

function initJamSelesaiValidOption() {
    const jamMulaiPicked = document.getElementById('jam-mulai');
    const jamSelesaiValidSelect = document.getElementById('jam-selesai');

    jamMulaiPicked.addEventListener('input', () => {
        const jamMulai = jamMulaiPicked.value;
        jamSelesaiValidSelect.innerHTML = '<option selected disabled>Pilih jam selesai</option>';

        if (!jamMulai || jamValidList.length === 0) return;

        const jamSelesaiList = cariSemuaJamSelesai(jamMulai);

        jamSelesaiList.forEach(jam => {
            const option = document.createElement("option");
            option.value = jam;
            option.textContent = jam;
            jamSelesaiValidSelect.appendChild(option);
        });
    });
}

function cariSemuaJamSelesai(jamMulai) {
    const tambahSatuJam = (jam) => {
        const [h, m] = jam.split(":").map(Number);
        const date = new Date();
        date.setHours(h + 1, m, 0); // Pastikan menit tetap sama
        return date.toTimeString().slice(0, 5);
    };

    const hasil = [];
    let next = tambahSatuJam(jamMulai);
    const batasJam = "17:00";

    const jamValidFormatted = jamValidList.map(j => j.substring(0, 5));

    while (true) {
        if (next > batasJam) break;

        hasil.push(next);

        if (!jamValidFormatted.includes(next)) {
            break;
        }

        next = tambahSatuJam(next);
    }
    return hasil;
}

function initRuanganTersediaOption() {
    const datePicked = document.getElementById('tanggal-bimbingan');
    const jamMulaiPicked = document.getElementById('jam-mulai');
    const jamSelesaiValidSelect = document.getElementById('jam-selesai');
    const ruanganOption = document.getElementById('ruangan');

    jamSelesaiValidSelect.addEventListener('input', () => {
        const date = datePicked.value;
        const mulai = jamMulaiPicked.value;
        const selesai = jamSelesaiValidSelect.value;
        ruanganOption.innerHTML = '<option selected disabled>Pilih ruangan</option>';

        fetch(`/ambil-ruang-tersedia?tanggal=${date}&mulai=${mulai}&selesai=${selesai}`)
            .then(response => response.json())
            .then(data => {
                console.log(data);
                data.forEach(ruang => {
                    const option = document.createElement("option");
                    option.value = ruang.nomorRuangan;
                    option.text = ruang.namaRuangan;
                    ruanganOption.appendChild(option);
                });
            })
            .catch(error => console.log(error));
    })
}