#include "delay.h"
#include "key.h"
#include "sys.h"
#include "usart.h"
#include "uart2.h"
#include "uart3.h"
#include "ds18b20.h"
#include "wifi.h"
#include "gpio.h"
#include "stdio.h"
#include "glr_adc.h"
#include "rc522.h"
extern __IO u16 data_value[6];
u32		AD1_value, hy, trq, yw, AD5_value;
//温度报警值
 double	temperatureLimit;
 int	flameLimit;
 int	gasLimit;
 int	smokeLimit;
u8		ds18b20_state;
int		i;





/**
 *   连线说明：
 *   1--SDA  <----->PA4
 *   2--SCK  <----->PA5
 *   3--MOSI <----->PA7
 *   4--MISO <----->PA6
 *   5--悬空
 *   6--GND <----->GND
 *   7--RST <----->PB0
 *   8--VCC <----->VCC
 **/


/* a9 a10 单片机输入输出  a2 a3 wifi使用 PB1 - 蜂鸣器  PB8-报警灯 PB9-蓝灯 PC15-风扇 */

void init( void )
{
	DELAY_INIT(); //初始化时钟 延迟函数使用
	NVIC_PriorityGroupConfig( NVIC_PriorityGroup_2 ); /* 设置NVIC中断分组 */
	USART1_INIT( 115200 );//串口1初始化




//	ADC_INIT();
	LED1_INIT(); /* 初始化灯和风扇 */
//	LED2_INIT();
//	FAN_INIT();
	//BUZZER_INIT();
//	/* 报警灯亮 */
//	GPIO_SetBits( GPIOB, GPIO_Pin_8 );

	delay_ms(10000 );
	ESP_INIT();
//	ds18b20_state = DS18B20_Init(); //初始化温度传感器
//	GPIO_SetBits( GPIOB, GPIO_Pin_9 );
	printf("wifi初始化成功");
		RC522_Init();    
}


int main( void )
{ 

}


