<?php

namespace KataTest;

use Kata\BingoBoard;
use PHPUnit\Framework\TestCase;

class BingoTest extends TestCase
{
    private BingoBoard $board;

    public function testAnNewlyCreatedBoardIsNotInitialized(): void
    {
        $this->board = new BingoBoard(1, 1);
        $this->assertFalse($this->board->isInitialized());
    }

    public function testWhenAllFieldsAreSetTheBoardIsInitialized(): void
    {
        $anyValue = "42";
        $this->board = new BingoBoard(1, 1);
        $this->board->defineCell(0, 0, $anyValue);
        $this->assertTrue($this->board->isInitialized());
    }

    public function testWhenAllFieldsOnRectangularBoardAreSetItIsInitialized(): void
    {
        $one = "one, two, three";
        $two = "Bingo cells can contain any text";
        $this->board = new BingoBoard(1, 2);
        $this->board->defineCell(0, 0, $one);
        $this->board->defineCell(0, 1, $two);
        $this->assertTrue($this->board->isInitialized());
    }

    public function testADefinedCellCantBeRedefinedEvenIfItsTheSameValue(): void
    {
        $anyValue = "42";
        $this->board = new BingoBoard(1, 1);
        $this->board->defineCell(0, 0, $anyValue);

        $this->expectException(\RuntimeException::class);
        $this->expectExceptionMessageMatches('/already defined/');
        $this->board->defineCell(0, 0, $anyValue);
    }

    public function testDuplicateCellsAreNotAllowed(): void
    {
        $anyValue = "42";
        $this->board = new BingoBoard(2, 2);
        $this->board->defineCell(0, 1, $anyValue);

        $this->expectException(\RuntimeException::class);
        $this->expectExceptionMessageMatches('/' . preg_quote($anyValue . " already present at 0,1") . '/');
        $this->board->defineCell(1, 0, $anyValue);
    }

    public function testANonInitializedBoardCannotBeMarked(): void
    {
        $this->board = new BingoBoard(1, 1);

        $this->expectException(\RuntimeException::class);
        $this->expectExceptionMessageMatches('/not initialized/');
        $this->board->markCell(0, 0);
    }

    public function testWhenAllCellGetsMarkedItIsMarked(): void
    {
        $anyValue = "42";
        $this->board = new BingoBoard(1, 1);
        $this->board->defineCell(0, 0, $anyValue);
        $this->board->markCell(0, 0);
        $this->assertTrue($this->board->is_marked(0, 0));
    }
}
