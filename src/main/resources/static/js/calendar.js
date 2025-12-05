(() => {
    generateTimeLabels();
    generateDayLabels();
    generateTimeSlots();
})();

function generateTimeLabels() {
    const container = document.querySelector('#time');
    const startHour = 7;
    const endHour = 18;

    for (let hour = startHour; hour < endHour; hour++) {
        const label = document.createElement('div');
        label.textContent = hour;
        container.appendChild(label);
    }
}

function generateDayLabels() {
    const days = ['Senin', 'Selasa', 'Rabu', 'Kamis', 'Jumat'];
    const container = document.querySelector('#calendar-header');

    for (const day of days) {
        const label = document.createElement('div');
        label.textContent = day;
        container.appendChild(label);
    }
}

function generateTimeSlots() {
    const slotContainer = document.getElementById("slot");
    const days = 5;
    const hours = 11;
    const startHour = 7;

    let slots = [];

    for (let h = 0; h < hours; h++) {
        for (let d = 0; d < days; d++) {

            const div = document.createElement("div");
            div.classList.add("slot-block");
            div.dataset.day = d;
            div.dataset.hour = startHour + h;

            slotContainer.appendChild(div);
            slots.push(div);
        }
    }
    implementDragLogic(slots);
}

function implementDragLogic(slots) {
    let isSelecting = false;
    let startSlot = null;
    let startDay = null;

    slots.forEach(slot => {

        slot.addEventListener("mousedown", (e) => {
            isSelecting = true;
            startSlot = slot;
            startDay = slot.dataset.day;

            clearSelection(slots);
            slot.classList.add("selected");
        });

        slot.addEventListener("mouseenter", (e) => {
            if (!isSelecting) return;

            // mencegah drag horizontal
            if (slot.dataset.day !== startDay) return;

            highlightRange(startSlot, slot, slots);
        });

        slot.addEventListener("mouseup", (e) => {
            if (!isSelecting) return;
            isSelecting = false;

            openForm(startSlot, slot);
        });
    });

    document.addEventListener("mouseup", () => {
        isSelecting = false;
    });
}

function clearSelection(slots) {
    slots.forEach(s => s.classList.remove("selected"));
}

function highlightRange(start, end, slots) {
    clearSelection();

    const startHour = parseInt(start.dataset.hour);
    const endHour = parseInt(end.dataset.hour);
    const day = start.dataset.day;

    const [minH, maxH] = [
        Math.min(startHour, endHour),
        Math.max(startHour, endHour)
    ];

    slots.forEach(s => {
        if (s.dataset.day === day &&
            parseInt(s.dataset.hour) >= minH &&
            parseInt(s.dataset.hour) <= maxH) {
            s.classList.add("selected");
        }
    });
}

function openForm(start, end) {
    const startHour = parseInt(start.dataset.hour);
    const endHour = parseInt(end.dataset.hour);

    const begin = Math.min(startHour, endHour);
    const finish = Math.max(startHour + 1, endHour + 1); // minimal 1 jam

    // Kirim ke form popup
    document.getElementById("form-start").value = begin + ":00";
    document.getElementById("form-end").value = finish + ":00";

    // tampilkan popup
    document.getElementById("overlay-pengajuan").classList.add("show");
}