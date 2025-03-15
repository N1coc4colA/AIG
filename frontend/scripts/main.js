const messages = document.getElementById("messagingFlex");
const imaging = document.getElementById("imagingFlex");
const chatPromptField = document.getElementById("chat-inputArea");
const imgPromptField = document.getElementById("img-inputArea");
const ingredientsField = document.getElementById("rcp-ing-inputArea");
const restrictionsField = document.getElementById("rcp-not-inputArea");
const cuisineField = document.getElementById("rcp-type-inputArea");
const recipeArea = document.getElementById("recipeArea");
const converter = new showdown.Converter();
let img_gen_counter = 0;
let msg_gen_counter = 0;

function submitChat()
{
    const prompt = chatPromptField.value;

    const msgId = msg_gen_counter++;
    const msg = document.createElement("div");
    msg.classList.add("message", "user");
    msg.innerText = prompt;
    msg.id = "msg-" + msgId.toString();
    messages.appendChild(msg);

    const params = new URLSearchParams({
        prompt: prompt,
    });

    return fetch("http://localhost:3306/chat?" + params.toString())
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        return response.text();
    })
    .then(data => {
        const assMsg = document.createElement("div");
        assMsg.classList.add("message", "assistant");
        assMsg.innerText = data;
        messages.appendChild(assMsg);

        return true;
    })
    .catch(error => {
        document.getElementById("msg-" + msgId.toString()).classList.add("failure");
        console.error("Error in request:", error);
        return false;
    });
}

function generateImage()
{
    const prompt = imgPromptField.value;
    const params = new URLSearchParams({
        prompt: prompt,
    });

    return fetch("http://localhost:3306/image?" + params.toString())
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        return response.json();
    })
    .then(data => {
        data.forEach(url => {
            const container = document.createElement("div");
            container.classList.add("col-10", "col-md-5", "col-xl", "img-gen", "p-0", "", "");

            const img = document.createElement("img");
            img.classList.add("img-fluid", "roundit");
            img.src = url;
            img.alt = "AI-Generated Image";
            img.id = "img-gen-" + (img_gen_counter++).toString();

            container.appendChild(img);
            imaging.appendChild(container);
        });

        return true;
    })
    .catch(error => {
        console.error("Error in request:", error);
        return false;
    });
}

function generateRecipe()
{
    const ingredients = ingredientsField.value;
    const restrictions = restrictionsField.value;
    const cuisine = cuisineField.value;
    const params = new URLSearchParams({
        cuisine: cuisine,
        ingredients: ingredients,
        dietaryRestriction: restrictions,
    });

    return fetch("http://localhost:3306/recipe?" + params.toString())
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        return response.text();
    })
    .then(data => {
        recipeArea.innerHTML = converter.makeHtml(data);
        return true;
    })
    .catch(error => {
        console.error("Error in request:", error);
        return false;
    });
}
