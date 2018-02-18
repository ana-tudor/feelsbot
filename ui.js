
// document.getElementById("imgClickAndChange").onclick = function(){changeBear()};
// bear = bear.onclick = function(){changeBear()};

function changeBear() {
    var bear = document.getElementById("imgClickAndChange");
    if (bear.getAttribute("src") != "bears/bear.gif") {
        bear.src = "bears/bear.gif";
        console.log("hijfdsij");
    } else {
        bear.src = "bears/bear1.png";
        console.log("hi111111");
    }
}
