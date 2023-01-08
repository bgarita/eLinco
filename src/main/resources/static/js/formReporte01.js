

const setDate = () => {
    const enero = 1;
    const diciembre = 12;
    const year = document.getElementById('year');
    const month = document.getElementById('month');

    let monthValue = new Date().getMonth() + 1; // Enero=0
    let yearValue = new Date().getFullYear();

    // Establecer el periodo anterior ya que normalmente, la contabilidad procesa
    // los datos del mes anterior.
    if (monthValue === enero) {
        monthValue = diciembre;
        yearValue--;
    } else {
        monthValue--;
    }

    month.value = monthValue;
    year.value = yearValue;
};

const setAnchor = () => {
    const a = document.getElementById('link');
    const year = document.getElementById('year');
    const month = document.getElementById('month');
    const receptor = document.getElementById('receptor');
    const emisor = document.getElementById('emisor');
    const tipoDoc = document.getElementById('tipoDoc');

    a.href = `/factura/reporte01/?year=${year.value}&month=${month.value}&receptor=${receptor.value}&emisor=${emisor.value}&tipoDoc=${tipoDoc.value}`;
};

$(document).ready(function () {
    setDate();
    setAnchor();
    $('#month').on('change', function(){
        setAnchor();
    });
    $('#year').on('change', function(){
        setAnchor();
    });
    $('#receptor').on('change', function(){
        setAnchor();
    });
    $('#emisor').on('change', function(){
        setAnchor();
    });
    $('#tipoDoc').on('change', function(){
        setAnchor();
    });
});
