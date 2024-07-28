/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

const Button1=document.querySelector('.button1');
const Button2=document.querySelector('.button2');
const Button3=document.querySelector('.button3');
const Button4=document.querySelector('.button4');
const Button5=document.querySelector('.button5');
console.log(Button5);

//get  product

const AddProduct=document.querySelector('#AddForm');
const UpdateProduct=document.querySelector('#UpdateForm');
const checkorder=document.querySelector('#order-table');

Button1.addEventListener('click',function(){
    AddProduct.style.display='block';
    UpdateProduct.style.display='none';
    checkorder.style.display='none'; 
});

Button2.addEventListener('click',function(){
    AddProduct.style.display='none';
    UpdateProduct.style.display='none';
    checkorder.style.display='none'; 
});

Button3.addEventListener('click',function(){
    AddProduct.style.display='none';
    UpdateProduct.style.display='block';
    checkorder.style.display='none'; 
});


Button5.addEventListener('click',function(){
    console.log("aman");
    AddProduct.style.display='none';
    UpdateProduct.style.display='none';
    checkorder.style.display='block'; 
    
    async function OrderData(){
        try{
            const url='Adminorderlist';
        
        
                const response=await fetch(url);
                if(!response.ok){
                    console.log("Api Doesn't Response");
                }else{
                     const data = await response.json();
                    console.log(data);
                    
                    const tbody=document.querySelector('.tbody');
                    tbody.innerHtml='';
                    
                     data.forEach(item=>{
                         const tr=document.createElement('tr');
                         const td=document.createElement('td');
                         td.textContent=item.ID;
                         const td1=document.createElement('td');
                         td1.textContent=item.ProductName;
                         const td2=document.createElement('td');
                         td2.textContent=item.ProductPrice;
                         const td3=document.createElement('td');
                         td3.textContent=item.Status;
                         const td4=document.createElement('td');
                         td4.textContent=item.UserName;
                         tr.appendChild(td);
                         tr.appendChild(td1);
                         tr.appendChild(td2);
                         tr.appendChild(td3);
                         tr.appendChild(td4);
                         tbody.appendChild(tr);
                     });
                    
                }
        
        }catch(error){
            console.log(error);
        }
    }
    
    OrderData();
    
    
});










