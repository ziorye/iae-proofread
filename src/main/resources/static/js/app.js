// Import all of Bootstrap's JS
import * as bootstrap from 'bootstrap'

import '../scss/app.scss';

import AOS from 'aos';
import 'aos/dist/aos.css'; // You can also use <link> for styles
AOS.init({
    duration: 1000,
    delay: 100,
});

import Typed from 'typed.js';
const options = {
    stringsElement: '#typed-strings',
    startDelay: 300,
    typeSpeed: 150,
    smartBackspace: false,
};
if (document.getElementById("typed-strings")) {
    new Typed("#typed", options);
}

import 'animate.css';

import $ from 'jquery';
window.$ = $;