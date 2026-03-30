package com.example.calculadora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadora.ui.theme.CalculadoraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CalculadoraUI(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun CalculadoraUI(modifier: Modifier = Modifier) {

    var tela by remember { mutableStateOf("") }
    var n1 by remember { mutableStateOf("") }
    var operacao by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        //reposta
        Text(
            text = if (tela.isEmpty()) "0" else tela,
            fontSize = 56.sp,
            color = Color(0xFF2986CC),
            textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            maxLines = 1
        )

        Column {

            //operacoes
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                BotaoOp("+", Modifier.weight(1f)) {
                    if (tela.isNotEmpty()) {
                        n1 = tela
                        operacao = "+"
                        tela = ""
                    }
                }
                BotaoOp("-", Modifier.weight(1f)) {
                    if (tela.isNotEmpty()) {
                        n1 = tela
                        operacao = "-"
                        tela = ""
                    }
                }
                BotaoOp("*", Modifier.weight(1f)) {
                    if (tela.isNotEmpty()) {
                        n1 = tela
                        operacao = "*"
                        tela = ""
                    }
                }
                BotaoOp("/", Modifier.weight(1f)) {
                    if (tela.isNotEmpty()) {
                        n1 = tela
                        operacao = "/"
                        tela = ""
                    }
                }

            Spacer(modifier = Modifier.height(16.dp))

            //numeros
            NumerosRow(listOf("1", "2", "3")) { tela += it }
            Spacer(modifier = Modifier.height(8.dp))

            NumerosRow(listOf("4", "5", "6")) { tela += it }
            Spacer(modifier = Modifier.height(8.dp))

            NumerosRow(listOf("7", "8", "9")) { tela += it }
            Spacer(modifier = Modifier.height(8.dp))

            //zerar
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Spacer(modifier = Modifier.weight(1f))
                NumeroBotao("0", Modifier.weight(1f)) { tela += "0" }
                Spacer(modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(8.dp))
            //resultado
            Button(
                onClick = {
                    try {
                        if (n1.isNotEmpty() && tela.isNotEmpty()) {

                            val calc = Calculadora(
                                n1.toDouble(),
                                tela.toDouble()
                            )

                            val resultado = when (operacao) {
                                "+" -> calc.somar()
                                "-" -> calc.subtrair()
                                "*" -> calc.multiplicar()
                                "/" -> calc.dividir()
                                else -> 0.0
                            }

                            tela = resultado.toString()
                        }
                    } catch (e: Exception) {
                        tela = "Erro no resultado."
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2986CC)
                )
            ) {
                Text("=", fontSize = 30.sp)
            }
        }
    }
}

@Composable
fun NumeroBotao(
    texto: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(80.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF9FC5E8)
        )
    ) {
        Text(
            texto,
            color = Color.Black,
            fontSize = 24.sp
        )
    }
}

@Composable
fun BotaoOp(
    texto: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(80.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF3D85C6)
        )
    ) {
        Text(
            texto,
            color = Color.Black,
            fontSize = 24.sp
        )
    }
}

@Composable
fun NumerosRow(
    numeros: List<String>,
    onClick: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        numeros.forEach {
            NumeroBotao(
                texto = it,
                modifier = Modifier.weight(1f)
            ) {
                onClick(it)
            }
        }
    }
}