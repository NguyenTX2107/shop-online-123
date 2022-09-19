// Toast function
function toast({
    title, 
    message, 
    type,
    duration
}) {
    const main = document.getElementById('toast');
    if (main) {
        const toast = document.createElement('div');

        const icons = {
            success: 'fas fa-check-circle',
            info: 'fas fa-info-circle',
            error: 'fas fa-exclamation-circle',
        }
        const icon = icons[type];
        const delay = (duration / 1000).toFixed(2);

        toast.classList.add('toast', 'toast--${type}');
        toast.style.animation = 'slideInLeft ease 0.3, fadeOut linear 1s ${delay}s forwards'; 

        toast.innerHTML = '<div class="toast__icon">';
            toast.innerHTML = '<i class="${icon}"></i>';
        toast.innerHTML = '/div';
        toast.innerHTML = '<div class="toast__body">';
            toast.innerHTML = '<h3 class="toast__title">${title}</h3>';
            toast.innerHTML = '<p class="toast__msg">${message}</p>';
        toast.innerHTML = '/div';
        toast.innerHTML = '<div class="toast__close">';
            toast.innerHTML = '<i class="fas fa-times"></i>';
        toast.innerHTML = '/div';

        main.appendChild(toast);

        //Auto remove toast
        const autoRemoveId = setTimeout(function() {
            main.removeChild(toast);
        }, duration + 1000);

        //Remove toast when clicked
        toast.onclick = function(e) {
            if (e.target.closest('.toast__close')) {
                main.removeChild(toast);
                clearTimeout(autoRemoveId);
            }
        }
    }
}

function sampleShowToast() {
    toast({
        title: 'Success',
        message: 'Hello',
        type: 'success',
        duration: 3000
    })
}