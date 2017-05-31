/* desarrollado por danielrosero

*/

#include <PololuLedStrip.h>

// Create an ledStrip object and specify the pin it will use.
PololuLedStrip<4> ledStrip;

// Create a buffer for holding the colors (3 bytes per color).
#define LED_COUNT 12 //Define el numero de pixeles a usar
rgb_color colors[LED_COUNT];
rgb_color color;
rgb_color nocolor;


//4 Niveles de colores

rgb_color colorLiso;
rgb_color colorRugoso;
rgb_color colorAccidentado;
rgb_color colorBache;

int numeroNiveles = 6;
boolean modoArcoiris = false;
boolean modoSoloColor = false;





void setup()
{
  // Start up the serial port, for communication with the PC.
  Serial.begin(9600);
  Serial.println("Ready to receive colors."); 

  color.red = 255;
  color.green = 0;
  color.blue = 0;

  nocolor.red = 0;
  nocolor.green = 0;
  nocolor.blue = 0;


  //      Defino los colores

  colorLiso.red = 71;
  colorLiso.green = 255;
  colorLiso.blue = 11;
  
  colorRugoso.red = 255;
  colorRugoso.green = 255;
  colorRugoso.blue = 0;
  
  colorAccidentado.red = 255;
  colorAccidentado.green = 102;
  colorAccidentado.blue = 0;
  
  colorBache.red = 255;
  colorBache.green = 0;
  colorBache.blue = 0;
  


  //****
}

void loop()
{
  // If any digit is received, we will go into integer parsing mode
  // until all three calls to parseInt return an interger or time out.

  // If any digit is received, we will go into integer parsing mode
  // until all three calls to parseInt return an interger or time out.
  if (Serial.available())
  {
    char c = Serial.peek();

    if(c=='n'){
      modoArcoiris=true;
      modoSoloColor=false;
    }

    if(c=='m'){
      modoArcoiris=false;
      modoSoloColor=true;
    }

    if(c=='u'){

      
      // Update the colors buffer.
      for(uint16_t i = 0; i < LED_COUNT; i++)
      {

       
          colors[i] = colorLiso;
   
     
      }

      // Write to the LED strip.
      ledStrip.write(colors, LED_COUNT);  
    }
    
     if(c=='i'){

      
      // Update the colors buffer.
      for(uint16_t i = 0; i < LED_COUNT; i++)
      {

       
          colors[i] = colorRugoso;
   
     
      }

      // Write to the LED strip.
      ledStrip.write(colors, LED_COUNT);  
    }
    
     if(c=='o'){

      
      // Update the colors buffer.
      for(uint16_t i = 0; i < LED_COUNT; i++)
      {

       
          colors[i] = colorAccidentado;
   
     
      }

      // Write to the LED strip.
      ledStrip.write(colors, LED_COUNT);  
    }
    
     if(c=='p'){

      
      // Update the colors buffer.
      for(uint16_t i = 0; i < LED_COUNT; i++)
      {

       
          colors[i] = colorBache;
   
     
      }

      // Write to the LED strip.
      ledStrip.write(colors, LED_COUNT);  
    }


    if(c=='q'){

      // Update the colors buffer.
      for(uint16_t i = 0; i < LED_COUNT; i++)
      {

        if(i<2){
          colors[i] = color;
        }
        else{
          colors[i]= nocolor;
        }
      }

      // Write to the LED strip.
      ledStrip.write(colors, LED_COUNT);  

    }

    if(c=='w'){

      // Update the colors buffer.
      for(uint16_t i = 0; i < LED_COUNT; i++)
      {

        if(i<4){
          colors[i] = color;
        }
        else{
          colors[i]= nocolor;
        }
      }

      // Write to the LED strip.
      ledStrip.write(colors, LED_COUNT);  

    }

    if(c=='e'){

      // Update the colors buffer.
      for(uint16_t i = 0; i < LED_COUNT; i++)
      {

        if(i<6){
          colors[i] = color;
        }
        else{
          colors[i]= nocolor;
        }
      }

      // Write to the LED strip.
      ledStrip.write(colors, LED_COUNT);  

    }

    if(c=='r'){

      // Update the colors buffer.
      for(uint16_t i = 0; i < LED_COUNT; i++)
      {

        if(i<8){
          colors[i] = color;
        }
        else{
          colors[i]= nocolor;
        }
      }

      // Write to the LED strip.
      ledStrip.write(colors, LED_COUNT);  

    }

    if(c=='t'){

      // Update the colors buffer.
      for(uint16_t i = 0; i < LED_COUNT; i++)
      {

        if(i<10){
          colors[i] = color;
        }
        else{
          colors[i]= nocolor;
        }
      }

      // Write to the LED strip.
      ledStrip.write(colors, LED_COUNT);  

    }

    if(c=='y'){

      // Update the colors buffer.
      for(uint16_t i = 0; i < LED_COUNT; i++)
      {

        if(i<12){
          colors[i] = color;
        }
        else{
          colors[i]= nocolor;
        }
      }

      // Write to the LED strip.
      ledStrip.write(colors, LED_COUNT);  

    }

    if(c=='x'){

      // Update the colors buffer.
      for(uint16_t i = 0; i < LED_COUNT; i++)
      {


        colors[i]= nocolor;

      }

      // Write to the LED strip.
      ledStrip.write(colors, LED_COUNT);  

    }



    if (!(c >= '0' && c <= '9'))
    {
      Serial.read(); // Discard non-digit character
      // digitalWrite(ledPin,HIGH);

    }



  }
}


