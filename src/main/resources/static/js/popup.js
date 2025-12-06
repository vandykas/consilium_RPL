(() => {
    const buttPengajuan = document.querySelector('#button-container > button:nth-child(2)');
    buttPengajuan.addEventListener('click', openPengajuanForm);

    const closeButton = document.querySelector('.button-group > button:nth-child(2)');
    closeButton.addEventListener('click', closePengajuanForm);
})();

function openPengajuanForm() {
    const form = document.querySelector('#overlay-pengajuan');
    form.classList.remove('hidden');
}

function closePengajuanForm() {
    const form = document.querySelector('#overlay-pengajuan');
    form.classList.add('hidden');
}