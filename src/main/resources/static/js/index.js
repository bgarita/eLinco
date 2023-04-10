
const setAnchor = () => {
    const a = document.getElementById('link');
    const nombreComercial = document.getElementById('nombreComercial');
    let nombreC = nombreComercial.value ? nombreComercial.value : '';
    
    a.href = `/factura/save/?nombreComercial=${nombreC}`;
};

$(document).ready(function () {
    setAnchor();
    $('#nombreComercial').on('blur', function(){
        setAnchor();
    });
});
