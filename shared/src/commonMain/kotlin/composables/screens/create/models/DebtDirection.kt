package composables.screens.create.models

enum class DebtDirection {
    ToPay, ToReceive;

    fun switch() = if (this == ToReceive) ToPay else ToReceive
}