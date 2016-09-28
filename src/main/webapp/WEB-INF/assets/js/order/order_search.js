$(function () {
  $('.order-item').on('click', function () {
    $(this).find('.order-detail').fadeToggle(600);
  });
});