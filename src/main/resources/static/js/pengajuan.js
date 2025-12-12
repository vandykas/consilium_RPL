(() => {
    initJamMulaiValidOption();
    initJamSelesaiValidOption();
})();

let jamValidList = []; // <-- simpan di sini

function initJamMulaiValidOption() {
    const datePicked = document.getElementById('tanggal-bimbingan');
    const jamValidSelect = document.getElementById('jam-mulai');

    datePicked.addEventListener('input', () => {
        const date = datePicked.value;
        jamValidSelect.innerHTML = '<option selected disabled>Pilih jam mulai</option>';
        jamValidList = []; // reset

        if (!date) return;

        fetch(`/ambil-jam-valid?tanggal=${date}`)
            .then(response => response.json())
            .then(data => {
                jamValidList = data.map(j => j.substring(0, 5));
                jamValidList.forEach(jam => {
                    const option = document.createElement("option");
                    option.value = jam;
                    option.text = jam;
                    jamValidSelect.appendChild(option);
                });
            })
            .catch(error => console.log(error));
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
        console.log(jamSelesaiList);
        console.log(jamMulai);

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
        date.setHours(h + 1);
        date.setMinutes(m);
        return date.toTimeString().slice(0, 5);
    };

    const hasil = [];
    let next = tambahSatuJam(jamMulai);
    const batasJam = "17:00";

    while (true) {
        if (next > batasJam) break;
        hasil.push(next);

        if (!jamValidList.includes(next)) {
            break;
        }

        next = tambahSatuJam(next);
    }
    return hasil;
}

