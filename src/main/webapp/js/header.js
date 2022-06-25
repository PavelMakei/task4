const currencyEL= document.getElementById('currency');
const curr_scaleEl= document.getElementById('curr_scale');
const curr_rateEl= document.getElementById('rate');
var rate;
var curr_scale;

function calculate() {
    const current_currency = currencyEL.value;

    fetch(`https://www.nbrb.by/api/exrates/rates/${current_currency}?parammode=2`)
        .then(res => res.json())
        .then(data => {
            // console.log(data);
            rate = data['Cur_OfficialRate'];
            curr_scale = data['Cur_Scale'];
            // console.log(curr_scale + ' : '+ rate);
            curr_scaleEl.innerText = curr_scale + ' ';
            curr_rateEl.innerText = '= '+rate+' BYN';
        });
}
// Event listeners
currencyEL.addEventListener('change', calculate);
calculate();

function goToCheckout() {
    window.location.href = '/controller?command=go_to_checkout';
}
function goToDeposit() {
    window.location.href = '/controller?command=go_to_deposit_money';
}
function goToLogin() {
    window.location.href = '/controller?command=go_to_login';
}
function clearCart() {
    window.location.href = '/controller?command=clear_cart';
}