.data
lf: .asciiz "\n"
value: .asciiz "n is "
steps: .asciiz "steps taken: "
high: .asciiz "Largest integer reached: "

.text
.globl main

main:
	addi $t0, $0, 1		# Register $t0 gets value of 1 (0 + 1)
	addi $t3, $0, 2		# Register $t3 gets value of 2
	addi $t4, $0, 3		# Register $t4 gets value of 3
	addi $t5, $0, 0		# Register $t5 gets value of 0

	li $v0, 5		# Input int into $v0
	syscall 
	add $t1, $0, $v0 	# Register $t1 gets input (n = input)
	add $t7, $0, $t1	# Register $t7 (big) gets value stored in $t1


loop:
	li   $v0, 4 		# print String
      	la   $a0, value		# loads value into $a0
      	syscall 		# prints message
	li $v0, 1		# prints int
	add  $a0, $0, $t1 	# concatenates the value of n with value
      	syscall 		# prints vlaue with value of n

	li   $v0, 4 		# print lf
      	la   $a0, lf 		# stores lf in $a0
      	syscall 		# prints new line

	beq $t0, $t1, end	# If n == 1 go to End
	andi $a0, $t1, 1	# AND $t1 and 1, put result into $a0
	beq $a0, $t0, odd	# If AND output is 1, go to ODD, else go to even

even:
	div $t1, $t3		# Divide n by 2, store in $lo
	mflo $t1		# mflo stores $lo into $t1
	addi $t5, $t5, 1	# increment step counter
	j loop			# Jump back to loop start 

odd:
	mult $t1, $t4		# Multiply n by 3, store in $t2
	mflo $t2		# mflo stores $lo in $t2
	addi $t1, $t2, 1	# Add 1 to n, store in $t1
	addi $t5, $t5, 1	# increment step counter
	
	slt $t6, $t1, $t7	# checks if $t1 less than $t7, if true stores 1.
	bne $t6, $t0, big	# if $t1 greater than $t7, go to big

	j loop			# Jump back to loop start

big:
	add $t7, $0, $t1	# $t7 gets value of $t1
	j loop			# jump back to loop start
	

end:
	li   $v0, 4 		# print lf
      	la   $a0, lf 		# stores lf in $a0
      	syscall 		# prints new line

	li   $v0, 4 		# print String
      	la   $a0, steps		# loads steps into $a0
      	syscall 		# prints message
	li $v0, 1		# prints int
	add  $a0, $0, $t5 	# concatenates the step counter with steps
      	syscall 		# prints steps with running total

	li   $v0, 4 		# print lf
      	la   $a0, lf 		# stores lf in $a0
      	syscall 		# prints new line

	li   $v0, 4 		# print String
      	la   $a0, high		# loads high into $a0
      	syscall 		# prints message
	li $v0, 1		# prints int
	add  $a0, $0, $t7 	# concatenates the largest int with high
      	syscall 		# prints steps with running total

	li $v0, 10
	syscall			# Ends program
