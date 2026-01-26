
    const slider = document.getElementById("co2Slider");
    const input = document.getElementById("co2Input");
    const output = document.getElementById("sliderValue");

    // Slider -> Input & Text
    slider.addEventListener("input", () => {
    input.value = slider.value;
    output.textContent = slider.value;
});

    input.addEventListener("input", () => {
    slider.value = input.value;
    output.textContent = input.value;
});

function confirmDelete(id, btn) {
    if (!confirm("Möchten Sie diesen Datensatz wirklich löschen?")) {
        return;
    }

    deleteRow(id, btn);
}

function deleteRow(id, btn) {
    fetch('/admin/delete/' + id, {
        method: 'DELETE',
        headers: {
            'X-Requested-With': 'XMLHttpRequest'
        }
    })
        .then(res => res.text())
        .then(text => {
            if (text === "OK") {
                btn.closest("tr").remove();
            } else {
                alert("Löschen nicht erlaubt: " + text);
            }
        });
}