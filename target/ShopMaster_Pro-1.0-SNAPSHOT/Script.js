/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

const containers = document.querySelector("#container");
const conimg = document.querySelector("#conimg");


// Function to apply CSS styles dynamically
function applyStyles() {
    containers.style.display = 'none'; // Initially hide the container
    conimg.style.transition = 'transform 1s ease'; // Smooth transition for transformation
    conimg.style.transform = 'perspective(1000px) rotateY(0deg)'; // Initial 3D transformation
}

// Function to handle the animation
function animation() {
    applyStyles(); // Apply initial styles

    // Hide the image after 3 seconds and apply 3D transformation
    setTimeout(() => {
        conimg.style.transform = 'perspective(1000px) rotateY(0deg)'; // Apply 3D rotation
        setTimeout(() => {
            conimg.style.display = 'none'; // Hide the image after transformation
        }, 1000); // Match this time with the transition duration
    }, 1000);

    // Show the container after 5 seconds
    setTimeout(() => {
        containers.style.display = 'block'; // Make the container visible
    }, 1000);
}

// Call the animation function to start the process
animation();




//Login button
const loginButton = document.querySelector('.login');

console.log(loginButton);
const hiddenButtonList =loginButton.querySelector('.hidden-button-list');
const angleDownIcon = document.querySelector('.fa-angle-down');

 loginButton.addEventListener('mouseenter', function() {
     hiddenButtonList.style.display = 'block';
     angleDownIcon.style.transform = 'rotate(180deg)'; 
     angleDownIcon.style.transition = 'transform 0.5s ease-in-out';
  });

 loginButton.addEventListener('mouseleave', function() {
    hiddenButtonList.style.display = 'none';
    angleDownIcon.style.transform = 'rotate(0deg)';
 });
 

//vertical line
const vertical = document.querySelector('.vertical');
const hiddenList = vertical.querySelector('.hidden-list');

// Show the list when mouse enters 'vertical'
vertical.addEventListener('mouseenter', function() {
  
   hiddenList.style.display = 'block';
});

// Hide the list when mouse leaves 'vertical'
vertical.addEventListener('mouseleave', function() {
    
        hiddenList.style.display = 'none';
        console.log("Executing hide action");
  
});




//Add Listener on Mobile And Laptop.
const Mobiles=document.querySelector('.mobiles');
const Mobilevalue=document.querySelector('.mobiles span');
const valuemobile=Mobilevalue.textContent;

Mobiles.addEventListener('click',function(){
     container.innerHTML = '';
     FetchData(valuemobile);
});
    
const Laptops=document.querySelector('.electornics');
const Laptopsvalue=document.querySelector('.electornics span');
const valueLaptops=Laptopsvalue.textContent;
console.log(valueLaptops);

Laptops.addEventListener('click',function(){
     container.innerHTML = '';
     FetchData(valueLaptops);
});




//imagecontainer for.
// Array of image URLs
const imageUrls = [
    'https://rukminim2.flixcart.com/fk-p-flap/1000/170/image/bf42fbdd3d37c8c3.jpg?q=20',
    'https://rukminim2.flixcart.com/fk-p-flap/1000/170/image/bf42fbdd3d37c8c3.jpg?q=20',
    'fgfdgdfg.webp',
    'cloud.webp'
    
    
];

// Get the image container element
const imageContainer = document.getElementById('imageContainer');



function displayImages() {
    let index = 0;

    function displayNextImage() {
        const imageUrl = imageUrls[index];
        
        // Create an image element
        const img = document.createElement('img');
        img.src = imageUrl;
        img.alt = `Image ${index + 1}`; 
        img.onload = () => {
            img.style.display = 'block'; // Show the image when loaded
        };
        // Append the image to the container
        imageContainer.innerHTML = ''; // Clear previous images
        imageContainer.appendChild(img);

      
        const delay = 1000; 

        // Move to the next image index, wrapping around at the end
        index = (index + 1) % imageUrls.length;

       
        setTimeout(displayNextImage, delay);
    }

   
    displayNextImage();
}


displayImages();


//Add Filter action.
const FilterButton = document.querySelector('#Filter');
const FilterList = document.querySelector('.Filter-List');

FilterButton.addEventListener('mouseenter', function() {
    FilterList.style.display = 'block';
});

FilterButton.addEventListener('mouseleave', function() {
    FilterList.style.display = 'none';
});

const LaptopBrand=document.querySelector('.LaptopBrand');
const MobileBrand=document.querySelector('.MobileBrand');
const Prices=document.querySelector('.Prices');
const Laptoplist=document.querySelector('.Laptoplist');
const Mobilelist=document.querySelector('.Mobilelist');
const Pricelist=document.querySelector('.Pricelist');
LaptopBrand.addEventListener('mouseenter', function () {
    displayList('Laptoplist');
});

MobileBrand.addEventListener('mouseenter', function () {
    displayList('Mobilelist');
});

Prices.addEventListener('mouseenter', function () {
    displayList('Pricelist');
});

function displayList(category) {
    // Hide all lists first
    Laptoplist.style.display = 'none';
    Mobilelist.style.display = 'none';
    Pricelist.style.display = 'none';

    // Show the selected list
    if (category === 'Laptoplist') {
        Laptoplist.style.display = 'block';
    } else if (category === 'Mobilelist') {
        Mobilelist.style.display = 'block';
    } else if (category === 'Pricelist') {
        Pricelist.style.display = 'block';
    }
}


    
// Function to collect selected items in a list
function collectSelectedItems(list) {
    return Array.from(list.querySelectorAll('input[type="checkbox"]:checked')).map(checkbox => checkbox.value);

}
 

//
//         Make API call with selected filters
 

const Spinner=document.querySelector('.loader');
//Api Fetched here
const container=document.getElementById("products-container");
      container.innerHTML='';
var Addvalue=document.querySelector('#Addvalue');
var currentValue = parseInt(Addvalue.innerHTML);
const totalprices=document.createElement('p');


const checkoutdata=document.getElementById('checkoutdata');
checkoutdata.innerHTML='';
console.log(checkoutdata);
const checoutbutton=document.querySelector('.checoutbutton');
console.log(checoutbutton);
let itemprice=0;
var itemcount=0;
var item=0;
var ItemNameArray=[];
var ItemPriceArray=[];
const FinalCheckout = document.querySelector('.Final-Checkout');
function search() {
    // Get the value from the search input field
    var searchTerm = document.querySelector('.search').value.trim();
    
    // Log the search term to the console for verification
    console.log('Search term entered:', searchTerm);
    container.innerHTML = '';

    
FetchData(searchTerm);

}

document.querySelectorAll('.Filter-List input[type="checkbox"]').forEach(function(checkbox){
    checkbox.addEventListener('change',function(){
       // Get selected filters from all lists
        const selectedLaptops  = collectSelectedItems(Laptoplist);
        const selectedMobiles  = collectSelectedItems(Mobilelist);
        const selectedPrices   = collectSelectedItems(Pricelist);

        // Example: Log selected filters
        console.log('Selected Laptops:', selectedLaptops);
        console.log('Selected Mobiles:', selectedMobiles);
        console.log('Selected Prices:', selectedPrices);
        
        console.log(selectedLaptops.length);
       
        
       container.innerHTML='';
       FetchData(" ",selectedLaptops,selectedMobiles,selectedPrices);
        
    });
});


async function FetchData(searchTerm='',selectedLaptops=[],selectedMobiles=[],selectedPrices=[]){
    Spinner.style.display='block';
    try{
          console.log('length of:',selectedLaptops.length);
          console.log('searchterm:',searchTerm);
        const urlParams = new URLSearchParams();

          if (searchTerm!==' ') {
            urlParams.append('searchTerm', searchTerm);
        }

        if (selectedLaptops.length > 0) {
            selectedLaptops.forEach(laptop => urlParams.append('Laptops', laptop));
        }
        if (selectedMobiles.length > 0) {
            selectedMobiles.forEach(mobile => urlParams.append('Mobiles', mobile));
        }
        if (selectedPrices.length > 0) {
            selectedPrices.forEach(price => urlParams.append('Prices', price));
        }

        const url = urlParams.toString() ? `FetchData?${urlParams.toString()}` : 'FetchData';
        console.log(url);

        const response=await fetch(url);
        if(!response.ok){
            console.log("Error in fetcvhing");
        }else{
            const data= await response.json();
            console.log(data);
            
           
      data.forEach(product => {
        
        const productDiv = document.createElement("div");
        productDiv.classList.add("productItem");
        
        
        const productImage = document.createElement("img");
        
        productImage.src = `data:image/jpeg;base64,${product.ProductImage}`;
        productImage.alt = "Sorry";
        productImage.style.height="90px";
        productImage.style.width="100%";
        
        const productName = document.createElement("h5");
        productName.textContent = product.ProductName;
        
        const productPrice = document.createElement("price");
        productPrice.textContent = `Rs.${product.ProductPrice}`;
        
        const productDescription = document.createElement("p");
        productDescription.textContent =  product.ProductDescription;
        
         const Addcartbutton=document.createElement('button');
         Addcartbutton.textContent="Add To Cart";
         Addcartbutton.classList.add("Addcartbutton");
        
        
        // Append elements to productDiv
        productDiv.appendChild(productImage);
        productDiv.appendChild(productName);
        productDiv.appendChild(productPrice);
        productDiv.appendChild(productDescription);
        productDiv.appendChild(Addcartbutton);
        
        
        // Append productDiv to productsContainer
        container.appendChild(productDiv);
        
       
     

       //Add eventListener on Add to Cart button
       
       Addcartbutton.addEventListener('click',function(){
           console.log("button clicked");
           var newvalue=++currentValue;
           Addvalue.innerHTML=newvalue;
           Addvalue.style.color='green';
           Addvalue.style.fontStyle='large';
           Addvalue.style.fontWeight='bolder';

           
          
           //checkout logic
           
           
           const checkout=document.createElement('div');
           checkout.classList.add("cherckoutitem");
           const Removecartbutton=document.createElement('button');
           const Buytbutton=document.createElement('button');
           Removecartbutton.classList.add('buttonremove');
           Buytbutton.classList.add('buttonremove');
           Removecartbutton.textContent='Remove Item';
           Buytbutton.textContent='Buy';
           
           checkout.innerHTML=`
            <img src='data:image/jpeg;base64,${product.ProductImage}' height='100px' width='80px'>
             <h4>${product.ProductName}<\h4>
           <h5>RS.${product.ProductPrice}<\h5>`;

           checkout.appendChild(Removecartbutton);
            checkout.appendChild(Buytbutton);
//             checkout.appendChild(productPrice);
           checkoutdata.appendChild(checkout);
           
          
           //Add Eventlistener on remove item;
           // Display accumulated itemprice in Final-Checkout element
            

                Removecartbutton.addEventListener('click', function() {
                    var newvalue = --currentValue;
                    Addvalue.innerHTML = newvalue;
                    checkoutdata.removeChild(checkout);

                    // Subtract product price from itemprice
                    itemprice -= parseFloat(product.ProductPrice);

                    // Update FinalCheckout with the updated itemprice
                    FinalCheckout.textContent = `Total: Rs.${itemprice}`;

                    // Hide FinalCheckout if itemprice is zero or less
                    if (itemprice <= 0) {
                        FinalCheckout.style.display = 'none';
                         checoutbutton.style.display='none';
                         suceess.style.display='none';
                    }
                });
                
                


                        Buytbutton.addEventListener('click', function() {
                            // Accumulate product price to itemprice
                            itemprice += parseFloat(product.ProductPrice);
                            item++;
                            itemcount = item; // Update itemcount with the current item count
                            
                           ItemNameArray.push(product.ProductName);
                           ItemPriceArray.push(product.ProductPrice);
                            // Update FinalCheckout with the updated itemprice
                            FinalCheckout.textContent = `Total: Rs.${itemprice}`;
                            FinalCheckout.style.display = 'block';
                            checoutbutton.style.display = 'block';
                        });

                       





                  });
       
       
       
       
       
       
            });
        }
       }catch(error){
        console.log("error in fetching Api");
    }
    Spinner.style.display='none';
}


FetchData();








    
 
      
//cart function implements.
const cart=document.getElementsByClassName('cart');

console.log(cart);
const itemcheckoutcontainer=document.getElementById('checkout-container');
const buttons =document.querySelector('.cart button');
console.log(itemcheckoutcontainer);
buttons.addEventListener('click' , function(){
   setTimeout(()=>{
        itemcheckoutcontainer.style.display='block';
         itemcheckoutcontainer.style.transitionduration= '3s';
   },2000);
});

const suceess=document.querySelector('.sucess');
//checoutbutton.addEventListener('click' ,function(){
//    checoutbutton.style.display='block';
//    
//    const sc=document.createElement('span');
//    sc.textContent='Successfully ordered';
//    suceess.appendChild(sc);
//    FinalCheckout.style.display='none';
//});

 checoutbutton.addEventListener('click', function() {
     const token = localStorage.getItem('accessToken');
     if (!token) {
        alert('Please log in first!');
        window.location.href = 'Login.html';
    } else {
        console.log(ItemNameArray); 
        console.log(ItemPriceArray);
         const data = {
                names: ItemNameArray,
                prices: ItemPriceArray
            };

     // Convert the object to a JSON string
       const jsonData = JSON.stringify(data);
    
        console.log(jsonData);
        
        fetch('orderReceive', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: jsonData
            });
            console.log(token);
        alert('Successfully Ordered!');
      
      }
 });



//My Profile Setup.

const MyProfile=document.getElementById('MyProfile');
console.log(MyProfile);
MyProfile.addEventListener('click',function(){
 const token = localStorage.getItem('accessToken');
 if(!token){
     alert('Please Login First..');
 }else{
     window.location.href='MyProfile.html';
 }
});

//Order setup

const Order=document.getElementById("Orders");
console.log(Order);

Order.addEventListener('click',function(){
    window.location.href='Order.html';
});