
(() => {
    const buttPengajuan = document.querySelector('#button-container > button:nth-child(2)');
    if (buttPengajuan) {
        buttPengajuan.addEventListener('click', openPengajuanForm);
    }
    const txtCekDetail = document.querySelectorAll('.riwayat');
    if (txtCekDetail) {
        txtCekDetail.forEach(btn => {
            btn.addEventListener('click', openPengajuanForm);
        });
    }

    const closeButton = document.querySelector('.button-group > button:nth-child(2)');
    if (closeButton) {
        closeButton.addEventListener('click', closePengajuanForm);
    }
    const xbutt = document.querySelector('.pop-up-headerR >p');
    if (xbutt) {
        xbutt.addEventListener('click', closePengajuanForm);
    }

})();

function openPengajuanForm() {
    const form = document.querySelector('#overlay-pengajuan');
    if (!form) {
        console.error("Popup dengan id #overlay-pengajuan TIDAK ditemukan di DOM!");
        return;
    }
    form.classList.remove('hidden');
}

function closePengajuanForm() {
    const form = document.querySelector('#overlay-pengajuan');
    form.classList.add('hidden');
}