package com.StephenKing.Books.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
