
      function validateForm(){
        if(document.signupform.Email.value == ""){
          alert("Enter Your Email");
          document.signupform.Email.focus();
          return false;
        }
        if(document.signupform.Pass.value == ""){
          alert("Enter Your Password");
          document.signupform.Pass.focus();
          return false;
        }
        if(document.signupform.Contact.value == ""){
          alert("Enter Your Contact Number");
          document.signupform.Contact.focus();
          return false;
        }
        if(isNaN(document.signupform.Contact.value)){
          alert("Enter Valid Contact Number");
          document.signupform.Contact.focus();
          return false;
        }

        document.signupform.submit();

      }
  