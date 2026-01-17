
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


