<?php

namespace KataTest;

use Kata\BingoBoard;
use PHPUnit\Framework\TestCase;

class BingoTestBDD extends TestCase
{
    private BingoBoard $board;

    public function testANewlyCreatedBoardIsNotInitialized(): void
    {
        $this->givenBingoBoardOfSize(1, 1);
        $this->thenBoardIsNotInitialized();
    }

    public function testWhenAllFieldsAreSetTheBoardIsInitialized(): void
    {
        $anyValue = "42";
        $this->givenBingoBoardOfSize(1, 1);
        $this->whenCellIsDefined(0, 0, $anyValue);
        $this->thenBoardIsInitialized();
    }

    public function testWhenAllFieldsOnRectangularBoardAreSetItIsInitialized(): void
    {
        $one = "one, two, three";
        $two = "Bingo cells can contain any text";
        $this->givenBingoBoardOfSize(1, 2);
        $this->whenCellIsDefined(0, 0, $one);
        $this->whenCellIsDefined(0, 1, $two);
        $this->thenBoardIsInitialized();
    }

    public function testADefinedCellCantBeRedefinedEvenIfItsTheSameValue(): void
    {
        $anyValue = "42";
        $this->givenBingoBoardOfSize(1, 1);
        $this->whenCellIsDefined(0, 0, $anyValue);

        $this->expectException(\RuntimeException::class);
        $this->expectExceptionMessageMatches('/already defined/');
        $this->whenCellIsDefined(0, 0, $anyValue);
    }

    public function testDuplicateCellsAreNotAllowed(): void
    {
        $anyValue = "42";
        $this->givenBingoBoardOfSize(2, 2);
        $this->whenCellIsDefined(0, 1, $anyValue);

        $this->expectException(\RuntimeException::class);
        $this->expectExceptionMessageMatches('/' . preg_quote($anyValue . " already present at 0,1") . '/');
        $this->whenCellIsDefined(1, 0, $anyValue);
    }

    public function testANonInitializedBoardCannotBeMarked(): void
    {
        $this->givenBingoBoardOfSize(1, 1);

        $this->expectException(\RuntimeException::class);
        $this->expectExceptionMessageMatches('/not initialized/');
        $this->whenCellIsMarked(0, 0);
    }

    public function testWhenAllCellGetsMarkedItIsMarked(): void
    {
        $anyValue = "42";
        $this->givenBingoBoardOfSize(1, 1);
        $this->whenCellIsDefined(0, 0, $anyValue);
        $this->whenCellIsMarked(0, 0);
        $this->thenCellIsMarked(0, 0);
    }

    private function givenBingoBoardOfSize(int $width, int $height): void
    {
        $this->board = new BingoBoard($width, $height);
    }

    private function whenCellIsDefined(int $x, int $y, string $value): void
    {
        $this->board->defineCell($x, $y, $value);
    }

    private function whenCellIsMarked(int $x, int $y): void
    {
        $this->board->markCell($x, $y);
    }

    private function thenBoardIsNotInitialized(): void
    {
        $this->assertFalse($this->boardInitializeState());
    }

    private function thenBoardIsInitialized(): void
    {
        $this->assertTrue($this->boardInitializeState());
    }

    private function boardInitializeState(): bool
    {
        return $this->board->isInitialized();
    }

    private function thenCellIsMarked(int $x, int $y): void
    {
        $this->assertTrue($this->board->isMarked($x, $y));
    }
}
