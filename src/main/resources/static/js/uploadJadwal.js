(() => {
    const buttonUpload = document.querySelector('button[class="button_blue"]');
    if (buttonUpload) {
        buttonUpload.addEventListener('click', openClosedForm);
    }
    const buttonClosed = document.querySelectorAll('.close-button-banner');
    if (buttonClosed) {
        for (let buttonClose of buttonClosed) {
            buttonClose.addEventListener('click', openClosedForm);
        }
    }
})();

function openClosedForm() {
    const form = document.querySelector('#uploadJadwalMahasiswa');
    form.classList.toggle('hidden');
}